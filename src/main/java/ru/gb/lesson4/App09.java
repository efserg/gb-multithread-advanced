package ru.gb.lesson4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App09 {

    public static void main(String[] args) {
        for (int n = 0; n < 10000; n++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        ExecutorService service = Executors.newFixedThreadPool(10); // создается не больше 10 потоков
        for (int n = 0; n < 10000; n++) {
            service.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();

        //       ExecutorService fixedService = Executors.newFixedThreadPool(10);
        //       ExecutorService singleService = Executors.newSingleThreadExecutor();
        //       ExecutorService cachedService = Executors.newCachedThreadPool();

    }
}
