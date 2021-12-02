package ru.gb.lesson5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class App09CyclicBarrier {
    // CyclicBarrier выполняет синхронизацию заданного количества потоков в одной точке.
    // Как только заданное количество потоков заблокировалось (вызовами метода await()),
    // с них одновременно снимается блокировка.
    public static void main(String[] args) {
        final int THREADS_COUNT = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREADS_COUNT);
        for (int i = 0; i < THREADS_COUNT; i++) {
            int index = i;
            new Thread(() -> {
                try {
                    System.out.println("Подготавливается " + index);
                    Thread.sleep(1000 + 500 * (int) (Math.random() * 10));
                    System.out.println("Готов " + index);
                    cyclicBarrier.await(); // 4 3 2 1 0
                    System.out.println("Поехал " + index);
                    Thread.sleep(1000 + 500 * (int) (Math.random() * 10));
                    System.out.println("Доехал " + index);
                    cyclicBarrier.await(); // 4 3 2 1 0
                    System.out.println("Гонка закончилась");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


}
