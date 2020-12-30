package cn.congee.api.common.reload;

import cn.congee.api.common.reload.annotation.StandardReload;
import cn.congee.api.common.reload.domain.AbstractStandardReloadObject;
import cn.congee.api.common.reload.domain.AnnotationReloadObject;
import cn.congee.api.common.reload.domain.InterfaceReloadObject;
import cn.congee.api.common.reload.domain.entity.ReloadItem;
import cn.congee.api.common.reload.domain.entity.SmartReloadResult;
import cn.congee.api.common.reload.interfaces.StandardReloadCommandInterface;
import cn.congee.api.common.reload.interfaces.StandardReloadThreadLogger;
import cn.congee.api.common.reload.interfaces.StandardReloadable;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

/**
 * SmartReloadManager 管理器
 * <p>
 * 可以在此类中添加 检测任务 以及注册 处理程序
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:57
 **/
public class StandardReloadManager {

    private Map<String, AbstractStandardReloadObject> tagReloadObject;

    private StandardReloadScheduler reloadScheduler;

    private StandardReloadThreadLogger logger;

    public StandardReloadManager(StandardReloadThreadLogger logger, int threadCount) {
        this.tagReloadObject = new ConcurrentHashMap<>();
        if (logger == null) {
            throw new ExceptionInInitializerError("SmartReloadLoggerImp cannot be null");
        }

        if (threadCount < 1) {
            throw new ExceptionInInitializerError("threadCount must be greater than 1");
        }

        this.logger = logger;
        this.reloadScheduler = new StandardReloadScheduler(this.logger, threadCount);
    }

    /**
     * 默认创建单个线程
     *
     * @param logger
     */
    public StandardReloadManager(StandardReloadThreadLogger logger) {
        this(logger, 1);
    }

    /**
     * 停止
     */
    public void shutdown() {
        reloadScheduler.shutdown();
    }

    /**
     * 添加任务
     *
     * @param command      SmartReloadCommand实现类
     * @param initialDelay 第一次执行前的延迟时间
     * @param delay        任务间隔时间
     * @param unit         延迟单位 TimeUnit 天、小时、分、秒等
     */
    public void addCommand(StandardReloadCommandInterface command, long initialDelay, long delay, TimeUnit unit) {
        reloadScheduler.addCommand(command, initialDelay, delay, unit);
    }

    /**
     * 注册  实现接口的方式
     *
     * @param tag
     * @param reloadable
     */
    public void register(String tag, StandardReloadable reloadable) {
        requireNonNull(reloadable);
        requireNonNull(tag);
        if (tagReloadObject.containsKey(tag)) {
            logger.error("<<SmartReloadManager>> register duplicated tag reload : " + tag + " , and it will be cover!");
        }
        tagReloadObject.put(tag, new InterfaceReloadObject(reloadable));
    }

    /**
     * 注册 要求此类必须包含使用了SmartReload注解的方法
     *
     * @param reloadObject
     */
    public void register(Object reloadObject) {
        requireNonNull(reloadObject);
        Method[] declaredMethods = reloadObject.getClass().getDeclaredMethods();
        if (declaredMethods != null) {
            for (int i = 0; i < declaredMethods.length; i++) {
                Method method = declaredMethods[i];
                StandardReload annotation = method.getAnnotation(StandardReload.class);
                if (annotation != null) {
                    String reloadTag = annotation.value();
                    this.register(reloadTag, new AnnotationReloadObject(reloadObject, method));
                }
            }
        }
    }

    private void register(String tag, AbstractStandardReloadObject reloadObject) {
        if (tagReloadObject.containsKey(tag)) {
            logger.error("<<SmartReloadManager>> register duplicated tag reload : " + tag + " , and it will be cover!");
        }
        tagReloadObject.put(tag, reloadObject);
    }

    /**
     * Reload 已注册的ReloadItem
     *
     * @param reloadItem
     * @return SmartReloadResult
     */
    public SmartReloadResult doReload(ReloadItem reloadItem) {
        AbstractStandardReloadObject reloadObject = tagReloadObject.get(reloadItem.getTag());
        if (reloadObject != null) {
            return reloadObject.reload(reloadItem);
        }
        // 返回注册结果
        return new SmartReloadResult(reloadItem.getTag(), reloadItem.getArgs(), reloadItem.getIdentification(), false, "No registered reload handler was found");
    }

}
