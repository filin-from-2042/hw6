package com.github.javarar.rejected.task;

import java.util.concurrent.*;

public class DelayedOnRejectedThreadExecutor {
    public static ExecutorService create(int nThreads) {
        return new ThreadPoolExecutor(nThreads,
                nThreads,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1),
                (r, executor) -> {
                    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                    scheduledExecutorService.schedule(() -> {
                                System.out.println("Executing scheduled retry");
                                executor.execute(r);
                            },
                            3000,
                            TimeUnit.MILLISECONDS);
                });
    }
}
