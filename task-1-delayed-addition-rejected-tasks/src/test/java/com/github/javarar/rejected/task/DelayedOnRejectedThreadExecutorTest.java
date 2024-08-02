package com.github.javarar.rejected.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

// тесты не очень надежны
public class DelayedOnRejectedThreadExecutorTest {

    @Test
    public void when_waitTask3_then_task3_isExecuted() throws InterruptedException {
        ExecutorService executor = DelayedOnRejectedThreadExecutor.create(1);

        Future<?> future = executor.submit(new Task("1"));
        Future<?> future2 = executor.submit(new Task("2"));
        Future<?> future3 = executor.submit(new Task("3"));
        Thread.sleep(5000);

        Assertions.assertTrue(future3.isDone());
    }

    @Test
    public void when_dontWaitTask3_then_task3_isNotDone() throws InterruptedException {
        ExecutorService executor = DelayedOnRejectedThreadExecutor.create(1);

        Future<?> future = executor.submit(new Task("1"));
        Future<?> future2 = executor.submit(new Task("2"));
        Future<?> future3 = executor.submit(new Task("3"));
        Thread.sleep(3000);

        Assertions.assertFalse(future3.isDone());
    }


    static class Task implements Runnable {

        final String id;

        public Task(String id) {
            this.id = id;
        }

        @Override
        public void run() {

            try {
                System.out.println("Запуск " + id);
                sleep(1_000);
                System.out.println("Завершение " + id);
            } catch (InterruptedException e) {
                System.out.println("Ошибка");
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task " + id;
        }
    }
}
