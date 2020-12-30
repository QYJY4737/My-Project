package cn.congee.api.common.reload;

import cn.congee.api.common.reload.interfaces.StandardReloadCommandInterface;
import cn.congee.api.common.reload.interfaces.StandardReloadThreadLogger;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Reload 调度器
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:00
 **/
public class StandardReloadScheduler {

    private ScheduledThreadPoolExecutor executor;

    private StandardReloadThreadLogger logger;

    StandardReloadScheduler(StandardReloadThreadLogger logger, int threadCount) {
        this.executor = new ScheduledThreadPoolExecutor(threadCount, new SmartReloadSchedulerThreadFactory());
        this.logger = logger;
    }

    void shutdown() {
        try {
            executor.shutdown();
        } catch (Throwable e) {
            logger.error("<<SmartReloadScheduler>> shutdown ", e);
        }
    }

    void addCommand(StandardReloadCommandInterface command, long initialDelay, long delay, TimeUnit unit) {
        executor.scheduleWithFixedDelay(new ScheduleRunnable(command, this.logger), initialDelay, delay, unit);
    }

    static class ScheduleRunnable implements Runnable {

        private StandardReloadCommandInterface command;

        private StandardReloadThreadLogger logger;

        public ScheduleRunnable(StandardReloadCommandInterface command, StandardReloadThreadLogger logger) {
            this.command = command;
            this.logger = logger;
        }

        @Override
        public void run() {
            try {
                command.doTask();
            } catch (Throwable e) {
                logger.error("", e);
            }
        }
    }

    static class SmartReloadSchedulerThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);

        private final ThreadGroup group;

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final String namePrefix;

        SmartReloadSchedulerThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "smartreload-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

}
