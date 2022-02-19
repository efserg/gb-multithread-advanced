package ru.gb.lesson4;

public class App05b {
    private long v;

    public void thread1() {
        v = -1;
    }

    public void thread2() {
        System.out.print(v + " ");
    }

    public static void main(String[] args) {
        final App05b app = new App05b();
        final Thread t1 = new Thread(() -> app.thread1());
        final Thread t2 = new Thread(() -> app.thread2());
        t1.start();
        t2.start();   }

    public void test() {

    }
}
