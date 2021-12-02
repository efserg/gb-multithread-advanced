package ru.gb.lesson5;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class App06RWLockSample2 {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static String message = "a";

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new App06RWLockSample2.WriterA());
        t1.setName("Writer A");

        Thread t2 = new Thread(new App06RWLockSample2.WriterB());
        t2.setName("Writer B");

        Thread t3 = new Thread(new App06RWLockSample2.Reader());
        t3.setName("Reader");
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
    }

    static class Reader implements Runnable {

        public void run() {

            if (lock.isWriteLocked()) {
                System.out.println("Write Lock Present.");
            }
            // если есть лок на запись, то читающий поток будет остановлен до его снятия
            lock.readLock().lock();

            try {
                long duration = (long) (Math.random() * 10000);
                System.out.println(Thread.currentThread().getName()
                        + "  Time Taken " + (duration / 1000) + " seconds.");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + ": " + message);
                lock.readLock().unlock();
            }
        }
    }

    static class WriterA implements Runnable {

        public void run() {
            // если есть лок на запись или на чтение, то пишущий поток будет остановлен здесь
            // до снятия этого лока
            lock.writeLock().lock();

            try {
                long duration = (long) (Math.random() * 10000);
                System.out.println(Thread.currentThread().getName()
                        + "  Time Taken " + (duration / 1000) + " seconds.");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                message = message.concat("a");
                lock.writeLock().unlock();
            }
        }
    }

    static class WriterB implements Runnable {

        public void run() {
            // если есть лок на запись или на чтение, то пишущий поток будет остановлен здесь
            // до снятия этого лока
            lock.writeLock().lock();

            try {
                long duration = (long) (Math.random() * 10000);
                System.out.println(Thread.currentThread().getName()
                        + "  Time Taken " + (duration / 1000) + " seconds.");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                message = message.concat("b");
                lock.writeLock().unlock();
            }
        }
    }
}

