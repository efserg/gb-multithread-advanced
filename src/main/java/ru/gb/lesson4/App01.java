package ru.gb.lesson4;

public class App01 {

    public static void main(String[] args) throws InterruptedException {
        // Создание и запуск потоков через наследование от классов Thread и интерфейса Runnable
        ThreadExampleClass t1 = new ThreadExampleClass();
        Thread t2 = new Thread(new RunnableExample());
        System.out.println("Begin");
        t1.start(); // распространенная ошибка - вызов метода run() вместо start(). В этом случае выполнение метода произойдет в том же потоке
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // создание потока через анонимный класс / лямбду
        final Thread doSomethingAnonymous = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Do something");
                // если в потоке возникнет исключение, то основной поток
                // остановлен не будет
                throw new RuntimeException("Something was wrong");
            }
        });
        // Попытка перехватить это исключение закончится неудачей
        try {
            doSomethingAnonymous.start();
        } catch (Throwable t) {
            System.out.println("Исключение в асинхронном вызове");
            t.printStackTrace();
        }
        Thread.sleep(1000);
        System.out.println("====");

        final Thread doSomethingLambda = new Thread(() -> System.out.println("Do something"));
        doSomethingLambda.start();
        System.out.println("End");
    }

}

class ThreadExampleClass extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class RunnableExample implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
