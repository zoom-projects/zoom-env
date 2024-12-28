package com.hb0730.zoom.quartz.task;

import cn.hutool.core.util.ZipUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hb0730.zoom.base.AppUtil;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.ext.services.dto.task.TaskInfo;
import com.hb0730.zoom.base.ext.services.proxy.SysProxyService;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.base.utils.JsonUtil;
import com.hb0730.zoom.oss.core.OssStorage;
import com.hb0730.zoom.oss.util.OssUtil;
import com.hb0730.zoom.poi.ExcelExport;
import com.hb0730.zoom.poi.model.ExpParams;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用【异步导出】任务执行控制器
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/25
 */
@Slf4j
@Getter
public abstract class BaseAbstractExpTask<T> implements ITask {
    @Autowired
    private OssStorage ossStorage;
    @Autowired(required = false)
    private SysProxyService sysProxyService;
    /**
     * 配置格式类型
     */
    private Class<T> confClass;

    @SuppressWarnings("unchecked")
    public BaseAbstractExpTask() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.confClass = (Class<T>) parameterizedType[0];
        }
    }

    @Override
    public R<?> doTask(TaskInfo task) throws Exception {
        //检索对象数据
        Map<String, Object> params = JsonUtil.DEFAULT.json2Obj(task.getDisContent(), new TypeReference<Map<String, Object>>() {
        });
        //取得需要导出的数据
        R<List<T>> status = getData(params);
        if (!status.isSuccess()) {
            return R.NG(status.getMessage());
        }
        //取得数据信息
        List<T> data = status.getData();
        ExpParams<T> expParams = getExpParams(data, task.getId());
        if (data.size() > expParams.getOverLimit()) {
            return R.NG(String.format("数量超上限[%s]，请修改筛选条件！", expParams.getOverLimit()));
        }
        //设置导出件数
        task.setDisCount("" + data.size());
        //生成文件压缩上传
        R<String> res = genExcelZipAndUpload(task, data,
                expParams);
        if (!res.isSuccess()) {
            task.setDisCount("0");
        }
        return res;
    }

    /**
     * 取得需要导出的数据
     *
     * @param params 查询条件
     * @return 需要导出的数据
     */
    protected abstract R<List<T>> getData(Map<String, Object> params);

    /**
     * 取得导出参数
     *
     * @param data 数据
     * @param id   任务ID
     * @return 导出参数
     */
    protected ExpParams<T> getExpParams(List<T> data, String id) {
        ExpParams<T> params = new ExpParams<>(getConfClass());
        params.setData(data);
        return params;
    }

    /**
     * 生成Excel文件并上传
     *
     * @param task 任务信息
     * @param data 数据
     * @return 生成结果 下载地址
     */
    private R<String> genExcelZipAndUpload(TaskInfo task,
                                           List<T> data, ExpParams<T> params) {
        if (CollectionUtil.isEmpty(data)) {
            return R.OK();
        }
        List<File> xlsFiles = new ArrayList<>();
        int rowSize = data.size();
        int maxRows = 10000;
        int sheetSize = rowSize % maxRows == 0 ? rowSize / maxRows : rowSize / maxRows + 1;
        for (int i = 0; i < sheetSize; i++) {
            int fromIndex = i * maxRows;
            int toIndex = Math.min(fromIndex + maxRows, rowSize);
            List<T> subList = data.subList(fromIndex, toIndex);
            String _fileName = String.format("%s-%s", i, task.getFileName());
            String filePath = AppUtil.getProperty("zoom.upload.path", "/tmp");
            File xlsFile = new File(new File(filePath), _fileName);
            xlsFiles.add(xlsFile);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(xlsFile);
                exportExcel(subList, out, params);
                out.flush();
            } catch (Exception e) {
                log.error("生成Excel文件失败", e);
                return R.NG("生成Excel文件失败");
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (Exception e) {
                        log.error("关闭文件流失败", e);
                    }
                }
            }
        }

        File zipFile = new File(buildDestinationZipFilePath(xlsFiles.get(0), null));
        // 压缩文件
        String destPath = zipFile.getAbsolutePath();
        zipFile = zip(xlsFiles, destPath, true);
        if (zipFile == null) {
            return R.NG("导出EXCEL压缩失败！");
        }
        // 上传文件
        String _filename = task.getLotNo() + ".zip";
        String objectKey = OssUtil.normalize(task.getFilePath() + "/" + _filename);
        String fileUrl = ossStorage.uploadFile(objectKey, zipFile);

        task.setFileName(_filename);
        task.setFileUrl(fileUrl);
        return R.OK();
    }

    /**
     * 生成Excel文件
     *
     * @param dataSet 数据
     * @param out     输出流
     * @param params  导出参数
     * @throws Exception 异常
     */
    private void exportExcel(List<?> dataSet, OutputStream out, ExpParams<T> params) throws Exception {
        ExcelExport ex = null;
        try {
            if (CollectionUtil.isNotEmpty(params.getSheets())) {
                ex = new ExcelExport(params.getSheets().get(0).getTitle(), params.getSheets().get(0).getEntityClass());
                ex.setData(params.getSheets().get(0).getData());
                for (int idx = 1; idx < params.getSheets().size(); ++idx) {
                    ExpParams<T> sheet = params.getSheets().get(idx);
                    ex.createSheet("Sheet" + idx, sheet.getTitle(), sheet.getEntityClass());
                    ex.setData(sheet.getData());
                }
            } else {
                ex = new ExcelExport(params.getTitle(), params.getEntityClass());
                ex.setData(dataSet);
            }
            ex.write(out);
        } finally {
            if (ex != null) {
                ex.close();
            }
        }
    }


    private static String buildDestinationZipFilePath(File srcFile, String destParam) {
        if (StringUtils.isEmpty(destParam)) {
            if (srcFile.isDirectory()) {
                destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
            } else {
                String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                destParam = srcFile.getParent() + File.separator + fileName + ".zip";
            }
        } else {
            createDestDirectoryIfNecessary(destParam); // 在指定路径不存在的情况下将其创建出来
            if (destParam.endsWith(File.separator)) {
                String fileName = "";
                if (srcFile.isDirectory()) {
                    fileName = srcFile.getName();
                } else {
                    fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                }
                destParam += fileName + ".zip";
            }
        }
        return destParam;
    }

    /**
     * 创建目标路径
     *
     * @param destParam 目标路径
     */
    private static void createDestDirectoryIfNecessary(String destParam) {
        File destDir;
        if (destParam.endsWith(File.separator)) {
            destDir = new File(destParam);
        } else {
            destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
        }
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    /**
     * 压缩文件
     *
     * @param src      源文件
     * @param dest     目标文件
     * @param isDelSrc 是否删除源文件
     * @return 压缩文件
     */
    private static File zip(List<File> src, String dest, boolean isDelSrc) {
        try {
            File destFile = new File(dest);
            ZipUtil.zip(destFile, true, src.toArray(new File[0]));
            if (isDelSrc) {
                for (File file : src) {
                    file.delete();
                }
            }
            return destFile;
        } catch (Throwable e) {
            return null;
        }
    }
}
