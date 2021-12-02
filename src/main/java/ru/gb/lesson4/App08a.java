package ru.gb.lesson4;

public class App08a {
    public static void main(String[] args) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Поток остановлен, закрываем ресурсы");
            }
        });
        thread.start();

//        thread.stop(); // Работает, но крайне не рекомендуется использовать!!!

        thread.interrupt(); // вызовет InterruptedException на спящем потоке, поэтому это может
        // сработать, если внутри потока вызван метод sleep или wait

    }
}
