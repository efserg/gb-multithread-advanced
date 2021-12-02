package ru.gb.lesson4;

public class App07 {
    private final Object monitor = new Object();
    private volatile int data = 0;
    private volatile boolean dataPrepared = false;

    public void dataReceive() {
        synchronized (monitor) {
            System.out.println("Waiting for next part of data...");
            try {
                while (!dataPrepared) {
                    monitor.wait();
                }
                System.out.println("Receive new data " + data);
                dataPrepared = false;
                monitor.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dataPrepare() {
        synchronized (monitor) {
            System.out.println("Data prepared: " + (++data));
            dataPrepared = true;
            monitor.notify();
            try {
                while (dataPrepared) {
                    monitor.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        App07 app = new App07();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                app.dataPrepare();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                app.dataReceive();
            }
        });

        t1.start();
        t2.start();
    }
}
