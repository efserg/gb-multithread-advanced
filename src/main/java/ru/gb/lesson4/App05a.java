package ru.gb.lesson4;

public class App05a {
    private static int number = 0;
    private static volatile boolean ready = false;
//    private static boolean ready = false; // такое же объявление, но без volatile "повесит" тред

    private static class Reader extends Thread {

        @Override
        public void run() {
            while (!ready) {
                ;
            }

            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Reader().start();
        number = 42;
        Thread.sleep(10);
        ready = true;
    }
}
