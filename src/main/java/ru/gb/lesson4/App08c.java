package ru.gb.lesson4;

public class App08c {
    public static void main(String[] args) throws InterruptedException {
        // Совместим два метода
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true && !Thread.currentThread().isInterrupted()) {
                    // полезная работа
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Поток остановлен, закрываем ресурсы");
            }
        });
        thread.start();
        Thread.sleep(1500);
        thread.interrupt();

    }
}
