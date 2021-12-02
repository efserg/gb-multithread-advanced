package ru.gb.lesson4;

public class App03 {
    public static void main(String[] args) {
        // Синхронизированные методы.
        SyncCounter sc = new SyncCounter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                sc.inc();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                sc.dec();
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
        System.out.println("p2_synch_counter: " + sc.value());
    }
}

class SyncCounter {
    private int c;

    public int value() {
        return c;
    }

    public SyncCounter() {
        c = 0;
    }

    public synchronized void inc() {
        c++;
    }

    public synchronized void dec() {
        c--;
    }
}
