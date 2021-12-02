package ru.gb.lesson5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

// Синхронизированные коллекции
public class App03Collection {
    public static void main(String[] args) {
        // 1) создание синхронизированной коллекции с помощью методов Collections
        final Collection<Object> synchronizedCollection = Collections.synchronizedCollection(new ArrayList<>());
        // таких методов в Collections довольно много
//        Collections.synch...
        // не эфективны и не рекомендуются к использованию

        final List<String> arrayList = new CopyOnWriteArrayList<>();
        final Set<String> set = new CopyOnWriteArraySet<>();
        final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        // и т.д.
//        new Concurrent...
    }

}

class ProducerConsumerSample {
    static class Producer {
        private ArrayBlockingQueue<String> queue;

        public Producer(ArrayBlockingQueue<String> queue) {
            this.queue = queue;
        }

        public void put(String x) {
            try {
                System.out.println("producer add: " + x);
                queue.put(x);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer {
        private ArrayBlockingQueue<String> queue;

        public Consumer(ArrayBlockingQueue<String> queue) {
            this.queue = queue;
        }

        public String get() {
            try {
                String str = queue.take();
                System.out.println("consumer get: " + str);
                return str;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void main(String[] args) {
        final ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<>(8);
        new Thread(() -> {
            Producer p = new Producer(abq);
            for (int i = 0; i < 12; i++) {
                try {
                    p.put(String.valueOf(i));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            Consumer c = new Consumer(abq);
            for (int i = 0; i < 12; i++) {
                try {
                    System.out.println(c.get());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
