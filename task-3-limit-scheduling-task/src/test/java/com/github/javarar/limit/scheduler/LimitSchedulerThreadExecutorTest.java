package com.github.javarar.limit.scheduler;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LimitSchedulerThreadExecutorTest {

    @Test
    public void should_shutdownAfterLimit() throws InterruptedException {
        LimitSchedulerThreadExecutor executor = ScheduledExecutors.create(2);
        AtomicInteger count = new AtomicInteger(0);

        executor.scheduleWithFixedDelayAndLimit(() -> {
            count.incrementAndGet();
            System.out.println("run");

        }, 5, 1000, TimeUnit.MILLISECONDS);
        Thread.sleep(10000);

        assertEquals(5, count.get());
        assertTrue(executor.isShutdown());
    }
}
