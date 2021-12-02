package ru.gb.lesson4;

public class App04 {
    public static void main(String[] args) {
        ATM atm = new ATM(100);
        User user1 = new User("#1");
        User user2 = new User("#2");
        User user3 = new User("#3");

        atm.info();
        Thread t1 = new Thread(() -> atm.getMoney(user1, 50));
        Thread t2 = new Thread(() -> atm.getMoney(user2, 50));
        Thread t3 = new Thread(() -> atm.getMoney(user3, 50));

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        atm.info();
    }
}

class User {
    private final String name;

    public String getName() {
        return name;
    }

    public User(String name) {
        this.name = name;
    }
}

class ATM {
    private int money;

    public ATM(int money) {
        this.money = money;
    }

    public /* synchronized */ void getMoney(User user, int amount) {
        if (money >= amount) {
            // Время на проверку денег
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            money -= amount;
            System.out.println("to " + user.getName() + ": " + amount);
        } else {
            System.out.println("to " + user.getName() + ": we need more gold...");
        }
    }

    public void info() {
        System.out.println("ATM: " + money);
    }
}