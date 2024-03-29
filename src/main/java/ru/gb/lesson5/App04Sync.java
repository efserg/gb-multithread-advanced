package ru.gb.lesson5;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Синхронайзеры
public class App04Sync {

    public static void main(String[] args) {

        // Интерфейс Lock из пакета java.util.concurrent – это продвинутый механизм синхронизации
        // потоков. По гибкости он выигрывает в сравнении с блоками синхронизации. Для работы с этим
        // интерфейсом необходимо создать объект одной из его реализаций
        Lock lock = new ReentrantLock();

        // Семафор - используется для ограничения потоками доступа к ресурсам.
        // Semaphore ограничивает количество потоков при работе с ресурсами.
        // Для этого служит счетчик. Если его значение больше нуля, то
        // потоку разрешается доступ, а значение уменьшается. Если счетчик
        // равен нулю, то текущий поток блокируется до освобождения
        // ресурса. Для получения доступа используется метод acquire(),
        // для освобождения – release()
        final Semaphore semaphore = new Semaphore(2);
        // Вырожденный случай семафора - монитор, там количество permits == 1
        // Для получения доступа используется метод acquire(), для
        // освобождения – release().

        // CountDownLatch позволяет потоку ожидать завершения операций,
        // выполняющихся в других потоках. Режим ожидания запускается
        // методом await(). При создании объекта определяется количество
        // требуемых операций, после чего уменьшается при вызове метода
        // countDown(). Как только счетчик доходит до нуля, с ожидающего
        // потока снимается блокировка.
        final CountDownLatch countDownLatch = new CountDownLatch(5);

        // CyclicBarrier выполняет синхронизацию заданного количества
        // потоков в одной точке. Как только заданное количество потоков
        // заблокировалось (вызовами метода await()), с них одновременно
        // снимается блокировка
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);


    }

}
