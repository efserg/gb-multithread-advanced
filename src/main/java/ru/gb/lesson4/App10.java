package ru.gb.lesson4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App10 {
    public static void main(String[] args) {
        // рекомендуемый способ остановки ExecutorService

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.shutdown(); // пробуем корректно завершить работу (пока есть незаконченные задачи, сервис не остановится)
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) { // ждем 800 секунд
                executorService.shutdownNow(); // немедленно завершает работу
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
