package ru.gb.lesson4;

public class App08b {
    public static void main(String[] args) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true && !Thread.currentThread().isInterrupted()) {
                    // полезная работа
                }
                System.out.println("Поток остановлен, закрываем ресурсы");
            }
        });
        thread.start();

        thread.interrupt(); // сейчас это уже просто так не сработает. Нужна
        // обработка флага isInterrupted

    }
}