package com.urise.webapp;

public class DeadLock {
    public String name;

    public DeadLock(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    synchronized void Lock(DeadLock db) {
        String threadName = Thread.currentThread().getName();

        System.out.println("Поток " + threadName + ", метод - Lock, объект " + db.getName());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(threadName + " прерван");
        }
        System.out.println("Поток " + threadName + " - попытка вызова метода Lock объекта " + this.name);
        db.Lock(db);
        System.out.println("Блокировка не сработала");
    }

    public static void main(String[] args) {
        System.out.println("Запущен главный поток - " + Thread.currentThread().getName());
        DeadLock db1 = new DeadLock("1");
        DeadLock db2 = new DeadLock("2");
        new Thread(() -> {
            System.out.println("Запущен поток - " + Thread.currentThread().getName());
            db1.Lock(db2);
        }).start();
        db2.Lock(db1);
    }
}

