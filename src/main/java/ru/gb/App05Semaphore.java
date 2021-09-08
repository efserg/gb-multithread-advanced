package ru.gb;

import java.util.concurrent.Semaphore;

public class App05Semaphore {
    public static void main(String[] args) {
        final Semaphore smp = new Semaphore(4);
        Runnable limitedCall = new Runnable() {
            int count = 0;
            public void run() {
                int time = 3 + (int)(Math.random() * 4.0);
                int num = count++;
                try {
                    // в этой секции поток захватывает семафор,
                    // если в нем есть свободные места, если мест нет, ждет пока не появится место
                    smp.acquire();
                    System.out.println("Поток #" + num + " начинает выполнять очень долгое действие " + time + " сек.");
                    Thread.sleep(time * 1000); // делаем вид, что поток выполняет важную задачу
                    System.out.println("Поток #" + num + " завершил работу!");
                    // освобождаем семафор, чтобы другой поток мог его занять
                    smp.release();
                } catch (InterruptedException intEx) {
                    intEx.printStackTrace();
                }
            }
        };
        for (int i = 0; i < 10; i++)
            new Thread(limitedCall).start(); // пытаемся запустить одновременно 10 потоков
    }


}
