package ru.gb.lesson4;

public class App02 {
    public static void main(String[] args) {

        // Race condition - возникает при совместном доступе к одному ресурсу
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.inc();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.dec();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("counter: " + counter.value());
    }
}

class Counter {
    private int c;

    public int value() { return c; }

    public Counter() {
        c = 0;
    }

    public void inc() {
        c++;
    }

    public void dec() {
        c--;
    }
}