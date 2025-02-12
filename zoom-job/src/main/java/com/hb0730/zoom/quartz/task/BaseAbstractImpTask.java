package com.hb0730.zoom.quartz.task;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.exception.ZoomException;
import com.hb0730.zoom.base.ext.services.dto.task.TaskInfo;
import com.hb0730.zoom.base.utils.JsonUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.oss.core.OssStorage;
import com.hb0730.zoom.oss.util.OssUtil;
import com.hb0730.zoom.poi.ExcelImport;
import com.hb0730.zoom.poi.model.ImportParams;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 通用【异步导入】任务执行控制器
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/25
 */
@Slf4j
public abstract class BaseAbstractImpTask<T> implements ITask {

    @Autowired
    private OssStorage ossStorage;
    /**
     * 配置格式类型
     */
    private Class<T> confClass;

    @SuppressWarnings("unchecked")
    public BaseAbstractImpTask() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.confClass = (Class<T>) parameterizedType[0];
        }
    }

    @Override
    public R<?> doTask(TaskInfo task) throws Exception {
        //导入处理
        try {
            List<T> data = getData(task);
            if (data == null || data.isEmpty()) {
                return R.NG("导入数据为空");
            }
            //TODO 超出上限 返回失败

            //数据格式等校验
            return doImport(data, task);
        } catch (Exception e) {
            log.error("导入异常", e);
            if (StrUtil.isBlank(e.getMessage())) {
                return R.NG(e.toString());
            }
            return R.NG(e.getMessage());
        }
    }

    /**
     * 具体导入处理
     *
     * @param data 导入数据
     * @param task 任务信息
     * @return 处理结果
     */
    protected R<String> doImport(Collection<T> data, TaskInfo task) throws Exception {
        // 数据唯一标识
        Set<String> keys = new HashSet<>();
        // 行号
        int iRow = 0;
        // 错误信息
        // 失败原因{单号:失败原因}
        Map<String, String> errors = new LinkedHashMap<>();
        //批量插入list
        List<T> items = new ArrayList<>();
        // 循环处理数据
        for (T item : data) {
            // 行号
            iRow++;

            // 数据校验
            R<Boolean> status = doDataCheck(item, keys);
            if (status != null && !status.isSuccess()) {
                errors.put("第" + iRow + "行", status.getMessage());
                continue;
            }

            // 实际业务处理
            R<T> bizStatus = doBizCheck(item);
            if (bizStatus.isSuccess()) {
                items.add(bizStatus.getData());
            } else {
                errors.put("第" + iRow + "行", bizStatus.getMessage());
            }
        }

        // 处理件数
        task.setFailCount(data.size() - items.size());
        task.setDisCount(String.format("%s/%s", items.size(), data.size()));

        //验证通过且有数据则保存
        if (!items.isEmpty()) {
            R<Boolean> status = doSaveItems(items, task);
            if (!status.isSuccess()) {
                errors.put("数据保存失败", status.getMessage());
                task.setFailCount(task.getFailCount() + items.size());
                task.setDisCount(String.format("%s/%s", 0, data.size()));
            }
        }

        // 处理结果
        task.setMessage(errors.isEmpty() ? "处理成功" : JsonUtil.DEFAULT.toJson(errors));
        return R.OK(task.getFileName());
    }

    /**
     * 获取导入的数据
     *
     * @param task 任务信息
     * @return .
     * @throws Exception 异常
     */
    protected List<T> getData(TaskInfo task) throws Exception {
        String objectKey = OssUtil.normalize(task.getFilePath() + "/" + task.getFileName());
        // 下载文件
        InputStream inputStream = ossStorage.getFile(objectKey);
        if (inputStream == null) {
            throw new ZoomException("文件不存在");
        }
        ImportParams importParams = getImportParams();
        int startRow = importParams.getStartRow();
        int sheetIndex = importParams.getSheetIndex();
        int sheetNum = importParams.getSheetNum();
        List<T> data = new ArrayList<>();
        ExcelImport excelImport = null;
        try {
            excelImport = new ExcelImport(inputStream, startRow);
            for (int i = sheetIndex; i <= sheetNum - 1; i++) {
                excelImport.setSheet(i + "", startRow);
                data.addAll(excelImport.getDataList(getConfClass()));
            }
        } finally {
            if (excelImport != null) {
                excelImport.close();
            }
            inputStream.close();
        }
        return data;

    }

    /**
     * 取得配置格式类型
     */
    protected Class<T> getConfClass() {
        return confClass;
    }

    protected ImportParams getImportParams() {
        return new ImportParams();
    }

    /**
     * 数据格式等校验
     *
     * @param item 导入数据
     * @param keys 数据唯一标识
     * @return 处理结果
     */
    protected abstract R<Boolean> doDataCheck(T item, Set<String> keys);

    /**
     * 业务逻辑处理
     *
     * @param item 导入数据
     * @return 处理结果
     */
    protected abstract R<T> doBizCheck(T item);

    /**
     * 保存数据
     *
     * @param items 待保存数据
     * @return 处理结果
     */
    protected abstract R<Boolean> doSaveItems(@NotNull List<T> items, TaskInfo task) throws Exception;
}
