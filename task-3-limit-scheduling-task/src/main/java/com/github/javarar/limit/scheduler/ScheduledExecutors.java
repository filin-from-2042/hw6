package com.github.javarar.limit.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduledExecutors {

    public static LimitSchedulerThreadExecutor create(int corePoolSize) {
        return new LimitSchedulerThreadExecutorImpl(corePoolSize);
    }

    private  static class LimitSchedulerThreadExecutorImpl implements LimitSchedulerThreadExecutor {
        private final ScheduledExecutorService scheduledExecutorService;
        private final AtomicInteger counter = new AtomicInteger(0);

        private LimitSchedulerThreadExecutorImpl(int corePoolSize) {
            this.scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize);
        }

        @Override
        public void scheduleWithFixedDelayAndLimit(Runnable command, int limit, long delay, TimeUnit unit) {
            scheduledExecutorService.scheduleWithFixedDelay(() -> {
                if (counter.incrementAndGet() <= limit) {
                    command.run();
                } else {
                    scheduledExecutorService.shutdown();
                }
            }, delay, delay, unit);

        }

        @Override
        public boolean isShutdown() {
            return scheduledExecutorService.isShutdown();
        }
    }
}
