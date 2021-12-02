package ru.gb.lesson4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class App11 {

    // execute(Runnable runnable);
    // submit(Runnable runnable);
    // submit(Callable callable);
    // invokeAll(...).
    // invokeAny(...);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // Метод execute() принимает Runnable
        executorService.execute(() -> System.out.println("Асинхронная задача-1"));

        // метод submit() может также принимать Runnable, либо Callable.
        // Отличие в том, что Callable позволяет вернуть результат выполнения
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Асинхронная задача-2");
                Thread.sleep(100);
                return 1;
            }
        });

        // или тоже самое, но через лямбду
        Future<Integer> futureLambda = executorService.submit(() -> {
            System.out.println("Асинхронная задача-2");
            Thread.sleep(100);
            return 1;
        });

        System.out.println(future.isDone());
        // Метод get() останавливает текущий поток до получения результата
        System.out.println(future.get());
        System.out.println(future.isDone());

        // Callable дает нам возможность перехватить исключение, возникшее
        // в асинхронном вызове, непосредственно в основном потоке
        Future<String> futureEx = executorService.submit(() -> {
            System.out.println("Асинхронный вызов");
            int x = 10 / 0;
            return "Результат из потока";
        });
        try {
            System.out.println("future.get() = " + futureEx.get());
        } catch (ExecutionException e) {
            System.out.println("Исключение в асинхронном вызове");
            e.printStackTrace();
        }

        // invokeAll()
        List<Callable<String>> tasks = new ArrayList<>();

        final long start = System.currentTimeMillis();
        for (int i = 0; i < 10; ++i) {
            int finalI = i;
            tasks.add(() -> {
                Thread.sleep(100);
                return "i = " + finalI;
            });
        }
        final List<Future<String>> futures = executorService.invokeAll(tasks);
        // до завершения всех задач, основной поток останавливается
        System.out.println(System.currentTimeMillis() - start);

        futures.forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });


        // invokeAny() - принимает коллекцию Callable'ов и дожидается выполнения
        // одной задачи - ее результат возвращается
        final String res = executorService.invokeAny(tasks);
        System.out.println("invokeAny() = " + res);


        executorService.shutdown(); // без этого вызова, программа не остановится

        // рекомендуемый подход
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
