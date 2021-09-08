package ru.gb;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

// Атомарные типы - позволяют проводить атомарные операции над примитивными и
// ссылочными типами без применения синхронизации
public class App02Atomic {

    public static void main(String[] args) {
        final AtomicInteger aInteger = new AtomicInteger(42);
//        final int i = aInteger.incrementAndGet();
        final int i1 = aInteger.accumulateAndGet(3, (x, y) -> x * y);
        System.out.println(i1);

        AtomicLong al = new AtomicLong(42);
        AtomicIntegerArray aia = new AtomicIntegerArray(new int[]{1, 2, 3, 4});
        AtomicReference<String> atomicReference = new AtomicReference<>("abc");
    }

}
