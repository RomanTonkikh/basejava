package com.urise.webapp;

public class DeadLock {

    static class One {
        synchronized void doLock(Two two) {
            String name = Thread.currentThread().getName();
            System.out.println("Поток " + name + ", метод - One.doLock");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(name + " прерван");
            }
            System.out.println("Поток " + name + " - попытка вызова two.notLock");
            two.notLock();
        }

        synchronized void notLock() {
            System.out.println(toString() + "DeadLock не сработал");
        }
    }

    static class Two {
        synchronized void doLock(One one) {
            String name = Thread.currentThread().getName();
            System.out.println("Поток " + name + ", метод - Two.doLock");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(name + " прерван");
            }
            System.out.println("Поток " + name + " - попытка вызова one.notLock");
            one.notLock();
        }

        synchronized void notLock() {
            System.out.println("DeadLock не сработал");
        }
    }

    public static void main(String[] args) {
        System.out.println("Запущен поток - " + Thread.currentThread().getName());
        One one = new One();
        Two two = new Two();
        new Thread(() -> {
            System.out.println("Запущен поток - " + Thread.currentThread().getName());
            two.doLock(one);
        }).start();
        one.doLock(two);
    }
}

