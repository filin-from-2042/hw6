package com.github.javarar.limit.scheduler;

import java.util.concurrent.TimeUnit;

public interface LimitSchedulerThreadExecutor {
    void scheduleWithFixedDelayAndLimit(Runnable command, int limit, long delay, TimeUnit unit);
    boolean isShutdown();
}
