package ru.gb.lesson5;

import java.util.concurrent.CountDownLatch;

public class App08CDL {
    // CountDownLatch позволяет потоку ожидать завершения операций, выполняющихся в других
    // потоках. Режим ожидания запускается методом await(). При создании объекта определяется
    // количество требуемых операций, после чего уменьшается при вызове метода countDown().
    // Как только счетчик доходит до нуля, с ожидающего потока снимается блокировка.
    public static void main(String[] args) {
        final int THREADS_COUNT = 4; // задаем кол-во потоков
        final CountDownLatch cdl = new CountDownLatch(THREADS_COUNT); // задаем значение счетчика
        System.out.println("START");
        for (int i = 0; i < THREADS_COUNT; i++) {
            final int w = i;
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // считаем что выполнение задачи занимает 2 сек
                    cdl.countDown(); // как только задача выполнена, уменьшаем счетчик
                    System.out.println("THREAD #" + w + " - Ready");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        try {
            // ждем пока счетчик не сбросится в ноль, пока это не произойдет, будем стоять на этой строке
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("END"); // как только все потоки выполнили свои задачи - пишем сообщение
    }
}
