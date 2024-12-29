package com.hb0730.zoom.quartz.task;

import com.hb0730.zoom.base.AppUtil;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.enums.TaskCategoryEnums;
import com.hb0730.zoom.base.enums.TaskStateEnums;
import com.hb0730.zoom.base.ext.services.dto.task.TaskInfo;
import com.hb0730.zoom.base.ext.services.proxy.SysProxyService;
import com.hb0730.zoom.base.utils.DateUtil;
import com.hb0730.zoom.base.utils.JsonUtil;
import com.hb0730.zoom.quartz.event.CompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 任务执行器
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/25
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ZoomTaskExecutor implements ITask {
    @Autowired(required = false)
    private SysProxyService sysProxyService;
    /**
     * Task接口容器
     */
    private final Map<String, ITask> tasks = new ConcurrentHashMap<>();

    /**
     * 可重入读写锁
     */
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    /**
     * 处理中任务池
     */
    private final Map<String, Boolean> INPROCESSTASKS = new ConcurrentHashMap<>();


    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String getTaskType() {
        return "00";
    }

    @Override
    public R<String> doTask(TaskInfo task) {
        // 任务开始时间
        String taskStartTime = DateUtil.now();
        log.info(">>>异步任务[{}]开始{}>>>", task.getTaskNum(), taskStartTime);
        try {
            // 设置任务开始状态
            if (Boolean.FALSE.equals(sysProxyService.setTaskStartState(task.getId(), TaskStateEnums.T1.getCode(),
                    taskStartTime))) {
                String msg = String.format("异步任务[%s]独立事物设置任务开始状态失败,直接返回!", task.getTaskNum());
                log.warn(">>> {} <<<", msg);
                return R.NG(msg, task.getTaskNum());
            }
            // 任务状态
            task.setDisTimeBegin(taskStartTime);
            task.setDisTimeEnd("");
            task.setDisCount("0");
            task.setFailCount(0);
            task.setMessage("");

            // 任务处理
            R<?> status = getTask(task.getType()).doTask(task);

            if (StringUtils.isBlank(task.getMessage())) {
                task.setMessage(status.getMessage());
            }
            if (status.isSuccess()) {
                return R.OK(task.getMessage(), task.getTaskNum());
            } else {
                return R.NG(task.getMessage(), task.getTaskNum());
            }

        } catch (Exception e) {
            // 错误信息设置
            task.setMessage(String.format("异常终了:%s", e.getMessage()));
            task.setDisCount("0");
            task.setFailCount(0);
            return R.NG(task.getMessage(), task.getTaskNum());
        } finally {
            /* 任务执行结果信息 */
            task.setDisTimeEnd(DateUtil.now());
            task.setDisState(TaskStateEnums.T2.getCode());
            try {
                Thread.sleep(2000L);
            } catch (Throwable e) {
            }
            // 更新任务状态
            sysProxyService.updateTastImmediately(task);
            // 发布任务完成事件
            applicationContext.publishEvent(new CompletedEvent(task, this));

            // 释放任务锁
            INPROCESSTASKS.remove(task.getTaskNum());
            log.info("<<<异步任务[{}]结束{}<<<{}", task.getTaskNum(), task.getDisTimeEnd(), JsonUtil.DEFAULT.toJson(task));
        }
    }

    /**
     * 执行任务
     *
     * @param taskCategory 任务类型
     * @param appName      应用名称
     * @return 结果
     */
    public R<?> doTask(TaskCategoryEnums taskCategory, String appName) {
        TaskInfo task = getTask(taskCategory, appName);
        // 空处理
        if (task == null) {
            R<Object> ok = R.OK();
            ok.setMessage(String.format("[%s]有任务处理中 或 没有排队任务", taskCategory.getMessage()));
            return ok;
        }
        // 任务防止重入锁
        if (isInProcess(task.getTaskNum())) {
            String msg = String.format("异步任务[%s]线程竞争直接返回!", task.getTaskNum());
            log.warn(">>> {} <<<", msg);
            return R.NG(msg, task.getTaskNum());
        }

        // 任务处理
        return doTask(task);
    }


    /**
     * 获取任务
     *
     * @param taskCategory 任务类型
     * @param appName      应用名称
     * @return 任务信息
     */
    protected synchronized TaskInfo getTask(TaskCategoryEnums taskCategory, String appName) {
        TaskInfo workingTask = sysProxyService.getWorkingTask(taskCategory, appName);
        if (null != workingTask) {
            // 任务正在处理
            log.info("~~~~~~ [2] 排队任务查找: {} : {} 任务处理中", taskCategory.getMessage(), workingTask.getTaskNum());
            return null;
        }
        // 2 取得未处理的任务
        TaskInfo task = sysProxyService.getPengdingTask(taskCategory, appName);
        if (task == null) {
            log.info("~~~~~~ [2]排队任务查找: {} : 无排队任务", taskCategory.getMessage());
            return null;
        }
        log.info("~~~~~~ [2]排队任务查找: {} : {} 任务开始处理", taskCategory.getMessage(), task.getTaskNum());
        return task;
    }

    /**
     * 根据任务类型取得相应任务处理类
     *
     * @param taskType 任务类型
     * @return 任务处理类
     */
    protected ITask getTask(String taskType) {
        // 注册所有任务类型
        synchronized (tasks) {
            if (tasks.isEmpty()) {
                Collection<ITask> beans = AppUtil.getBeans(ITask.class);
                for (ITask bean : beans) {
                    if (!bean.getTaskType().equals("00")) {
                        tasks.put(bean.getTaskType(), bean);
                    }
                }

                tasks.put("00", new ITask() {
                    /** 取得可处理任务类型 */
                    public String getTaskType() {
                        return "00";
                    }

                    /** 啥也不干 */
                    @Override
                    public R<String> doTask(TaskInfo task) {
                        return R.OK();
                    }
                });
            }
        }

        // 取得对应的任务处理器
        ITask task = tasks.get(taskType);
        if (task == null) {
            task = tasks.get("00");
        }
        return task;
    }


    /**
     * 任务防止重入锁
     *
     * @param taskNum 任务号
     * @return 是否处理中的任务
     */
    private boolean isInProcess(String taskNum) {
        //假设处理中
        boolean result = true;

        boolean laststate = true;

        try {
            if (rwl.readLock().tryLock(5, TimeUnit.SECONDS)) {// 首先开启读锁，从缓存中去取
                try {
                    //任务是否处理中？
                    result = INPROCESSTASKS.containsKey(taskNum);
                    if (!result) { // 如果缓存中没有=不在处理中，释放读锁，上写锁
                        rwl.readLock().unlock();

                        if (rwl.writeLock().tryLock(3, TimeUnit.SECONDS)) {
                            try {
                                INPROCESSTASKS.put(taskNum, true); //标识任务在处理了
                                result = false;
                            } finally {
                                rwl.writeLock().unlock(); // 释放写锁
                            }
                        } else {
                            result = true; //TODO 没拿到写锁，就当任务在处理了吧
                        }

                        laststate = rwl.readLock().tryLock(5, TimeUnit.SECONDS); // 然后再上读锁
                    }
                } finally {
                    if (laststate) {
                        rwl.readLock().unlock(); // 最后释放读锁
                    }
                }
            }
        } catch (Throwable e) {
            return true;
        }

        return result;
    }
}
