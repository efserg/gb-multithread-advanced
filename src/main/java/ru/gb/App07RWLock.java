package ru.gb;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class App07RWLock {
    // Интерфейс java.util.concurrent.locks.ReadWriteLock – это продвинутый механизм для
    // блокировки потоков. Он позволяет множеству потоков одновременно читать данные, или только
    // одному потоку – их записывать. Ресурс открыт для чтения множеству потоков без риска ошибок.

    public static void main(String[] args) {
        final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        for (int i = 0; i < 3; i++) {
            int index = i;
            new Thread(() -> {
                readWriteLock.readLock().lock();
                try {
                    System.out.println("Начало чтения - " + index);
                    Thread.sleep(1000);
                    System.out.println("Завершение чтения - " + index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readWriteLock.readLock().unlock();
                }
            }).start();
        }
        for (int i = 0; i < 2; i++) {
            int index = i;
            new Thread(() -> {
                readWriteLock.writeLock().lock();
                try {
                    System.out.println("Начало записи - " + index);
                    Thread.sleep(1000);
                    System.out.println("Завершение записи - " + index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readWriteLock.writeLock().unlock();
                }
            }).start();
        }
    }
}