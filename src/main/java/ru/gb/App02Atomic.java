package ru.gb;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

// Атомарные типы - позволяют проводить атомарные операции над примитивными и
// ссылочными типами без применения синхронизации. Вместо синхронизации используется механизм
// Compare-And-Swap (или Compare-And-Set) - CAS

public class App02Atomic {

    public static void main(String[] args) {
        final AtomicInteger aInteger = new AtomicInteger(42);
        final int i = aInteger.incrementAndGet();
        System.out.println("i = " + i);
        final int i1 = aInteger.accumulateAndGet(10, (x, y) -> x * y);
        System.out.println("i1 = " + i1);

        AtomicLong al = new AtomicLong(42);
        AtomicIntegerArray aia = new AtomicIntegerArray(new int[]{11, 22, 33, 44});
        final int i2 = aia.decrementAndGet(2); // decrement and get element with index = 2
        System.out.println("i2 = " + i2);
        AtomicReference<String> atomicReference = new AtomicReference<>("abc");
        final String def = atomicReference.accumulateAndGet("def", (x, y) -> x + y);
        System.out.println("def = " + def);
    }

}
