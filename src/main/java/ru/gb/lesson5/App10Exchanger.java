package ru.gb.lesson5;

import java.util.concurrent.Exchanger;

public class App10Exchanger {
    // Exchanger (обменник) может понадобиться, для того, чтобы обменяться данными
    // между двумя потоками в определенной точки работы обоих потоков. Обменник —
    // обобщенный класс, он параметризируется типом объекта для передачи.

    // Обменник является точкой синхронизации пары потоков: поток, вызывающий у
    // обменника метод exchange() блокируется и ждет другой поток. Когда другой поток
    // вызовет тот же метод, произойдет обмен объектами: каждая из них получит
    // аргумент другой в методе exchange(). Стоит отметить, что обменник поддерживает
    // передачу null значения. Это дает возможность использовать его для передачи объекта
    // в одну сторону, или, просто как точку синхронизации двух потоков.

    // Пример (https://habr.com/ru/post/277669/)
    // Рассмотрим следующий пример. Есть два грузовика: один едет из пункта A в пункт D,
    // другой из пункта B в пункт С. Дороги AD и BC пересекаются в пункте E. Из пунктов A
    // и B нужно доставить посылки в пункты C и D. Для этого грузовики в пункте E должны
    // встретиться и обменяться соответствующими посылками.

    // Создаем обменник, который будет обмениваться типом String
    private static final Exchanger<String> EXCHANGER = new Exchanger<>();

    public static void main(String[] args) throws InterruptedException {
        // Формируем груз для 1-го грузовика
        String[] p1 = new String[]{"{посылка A->D}", "{посылка A->C}"};
        // Формируем груз для 2-го грузовика
        String[] p2 = new String[]{"{посылка B->C}", "{посылка B->D}"};

        //Отправляем 1-й грузовик из А в D
        new Thread(new Truck(1, "A", "D", p1)).start();
        Thread.sleep(100);

        //Отправляем 2-й грузовик из В в С
        new Thread(new Truck(2, "B", "C", p2)).start();
    }

    public static class Truck implements Runnable {
        private final int number;
        private final String dep;
        private final String dest;
        private final String[] parcels;

        public Truck(int number, String departure, String destination, String[] parcels) {
            this.number = number;
            this.dep = departure;
            this.dest = destination;
            this.parcels = parcels;
        }

        @Override
        public void run() {
            try {
                System.out.printf("В грузовик №%d погрузили: %s и %s.\n", number, parcels[0], parcels[1]);
                System.out.printf("Грузовик №%d выехал из пункта %s в пункт %s.\n", number, dep, dest);
                Thread.sleep(1000 + (long) Math.random() * 5000);
                System.out.printf("Грузовик №%d приехал в пункт Е.\n", number);

                // При вызове exchange() поток блокируется и ждет пока другой поток
                // вызовет exchange(), после этого произойдет обмен посылками
                parcels[1] = EXCHANGER.exchange(parcels[1]);
                System.out.printf("В грузовик №%d переместили посылку для пункта %s.\n", number, dest);
                Thread.sleep(1000 + (long) Math.random() * 5000);
                System.out.printf("Грузовик №%d приехал в %s и доставил: %s и %s.\n", number, dest, parcels[0], parcels[1]);
            } catch (InterruptedException e) {
            }
        }
    }
}
