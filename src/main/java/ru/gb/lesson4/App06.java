package ru.gb.lesson4;

public class App06 {
    private static final Object mon = new Object();

    public static void main(String[] args) {
        final Thread t1 = new Thread(() -> { // t1
            synchronized (mon) {
                try {
                    System.out.println("p1-1"); // выполнили
//                    mon.wait(); // t1 уснул
                    System.out.println("p1-2"); // выполнили
//                    mon.notify(); //
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final Thread t2 = new Thread(() -> { // t2
            synchronized (mon) {
                try {
                    System.out.println("p2-1"); // выполнили
//                    mon.notify(); // разбудили t1
//                    mon.wait(); // уснул t2
                    System.out.println("p2-2"); // выполнили
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
