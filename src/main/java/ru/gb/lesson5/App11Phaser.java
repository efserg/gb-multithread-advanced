package ru.gb.lesson5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Phaser;

public class App11Phaser {
    // Phaser (фазер), как и CyclicBarrier, является реализацией
    // шаблона синхронизации Барьер, но, в отличии от CyclicBarrier,
    // предоставляет больше гибкости. Этот класс позволяет синхронизировать
    // потоки, представляющие отдельную фазу или стадию выполнения общего
    // действия. Как и CyclicBarrier, Phaser является точкой синхронизации,
    // в которой встречаются потоки-участники. Когда все стороны
    // прибыли, Phaser переходит к следующей фазе и снова ожидает ее
    // завершения.
    //
    // Если сравнить Phaser и CyclicBarrier, то можно выделить следующие
    // важные особенности Phaser:
    // - каждая фаза (цикл синхронизации) имеет номер;
    // - количество сторон-участников жестко не задано и может меняться:
    //   поток может регистрироваться в качестве участника и отменять
    //   свое участие;
    // - участник не обязан ожидать, пока все остальные участники соберутся
    //   на барьере. Чтобы продолжить свою работу достаточно сообщить
    //   о своем прибытии;
    // - случайные свидетели могут следить за активностью в барьере;
    // - поток может и не быть стороной-участником барьера, чтобы ожидать
    //   его преодоления;
    // - у фазера нет опционального действия.

    // Пример (https://habr.com/ru/post/277669/)
    //   Есть пять остановок. На первых четырех из них могут стоять
    //   пассажиры и ждать автобуса. Автобус выезжает из парка и
    //   останавливается на каждой остановке на некоторое время. После
    //   конечной остановки автобус едет в парк. Нам нужно забрать
    //   пассажиров и высадить их на нужных остановках.

    // Сразу регистрируем главный поток
    private static final Phaser PHASER = new Phaser(1);

    // Фазы 0 (START) и 6 (FINISH) - это автобусный парк, 1 - 5 остановки

    public static final int START = 0;
    public static final int FINISH = 6;

    public static void main(String[] args) throws InterruptedException {
        List<Passenger> passengers = new ArrayList<>();

        // Сгенерируем пассажиров на остановках
        for (int i = 1; i < 5; i++) {
            final Random random = new Random();
            if (random.nextBoolean())
                // Этот пассажир выходит на следующей
                passengers.add(new Passenger(i, i + 1));

            if (random.nextBoolean())
                // Этот пассажир выходит на конечной
                passengers.add(new Passenger(i, 5));
        }

        for (int i = 0; i < 7; i++) {
            // int getPhase() — возвращает номер текущей фазы
            int currentBusStop = PHASER.getPhase();
            System.out.println("Остановка № " + currentBusStop);

            switch (i) {
                case START:
                    System.out.println("Автобус выехал из парка.");
                    // int arrive() — сообщает, что сторона завершила фазу, и
                    // возвращает номер фазы. При вызове данного метода поток не
                    // приостанавливается, а продолжает выполнятся
                    PHASER.arrive(); // В фазе 0 всего 1 участник - автобус
                    break;
                case FINISH:
                    System.out.println("Автобус уехал в парк.");
                    // int arriveAndDeregister() — сообщает о завершении всех фаз
                    // стороной и снимает ее с регистрации. Возвращает номер текущей
                    // фазы
                    PHASER.arriveAndDeregister(); // Снимаем главный поток, ломаем барьер
                    break;
                default:

                    for (Passenger passenger : passengers)
                        // Проверяем, есть ли пассажиры на остановке
                        if (passenger.departure == currentBusStop) {
                            // int register() — регистрирует нового участника,
                            // который выполняет фазы. Возвращает номер текущей фазы;
                            // Регистрируем поток, который будет участвовать в фазах
                            PHASER.register();
                            passenger.start();        // и запускаем
                        }
                    // int arriveAndAwaitAdvance() — указывает что поток завершил
                    // выполнение фазы. Поток приостанавливается до момента, пока все
                    // остальные стороны не закончат выполнять данную фазу. Точный
                    // аналог CyclicBarrier.await(). Возвращает номер текущей фазы;
                    PHASER.arriveAndAwaitAdvance(); // Сообщаем о своей готовности
            }
        }
    }

    public static class Passenger extends Thread {
        private final int departure; // остановка, на которой пассажир садится в автобус
        private final int destination; // остановка, на которой пассажир выходит

        public Passenger(int departure, int destination) {
            this.departure = departure;
            this.destination = destination;
            System.out.println(this + " ждёт на остановке № " + this.departure);
        }

        @Override
        public void run() {
            try {
                System.out.println(this + " сел в автобус.");
                // Пока автобус не приедет на нужную остановку (фазу)
                while (PHASER.getPhase() < destination) {
                    // заявляем в каждой фазе о готовности и ждем
                    PHASER.arriveAndAwaitAdvance();
                }
                Thread.sleep(1);
                System.out.println(this + " покинул автобус.");
                // int arriveAndDeregister() — сообщает о завершении всех фаз
                // стороной и снимает ее с регистрации. Возвращает номер текущей
                // фазы
                PHASER.arriveAndDeregister();   // Отменяем регистрацию на нужной фазе
            } catch (InterruptedException e) {
            }
        }

        @Override
        public String toString() {
            return "Пассажир{" + departure + " -> " + destination + '}';
        }
    }

}
