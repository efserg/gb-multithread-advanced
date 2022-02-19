package ru.gb.lesson4;

public class App08c {
    public static void main(String[] args) throws InterruptedException {
        // Совместим два метода
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    // полезная работа
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // Если метод interrupt() будет вызван на спящем потоке,
                        // то будет брошено исключение InterruptedException и
                        // флажок interrupted будет выставлен обратно в
                        // false (как будто поток не прерывался). Поэтому в
                        // блоке обработки исключения необходимо добавить вот
                        // такую строку
                        Thread.currentThread().interrupt();
                        // В противном случае проверка
                        // !Thread.currentThread().isInterrupted()
                        // вернет true и метод продолжит работу
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
