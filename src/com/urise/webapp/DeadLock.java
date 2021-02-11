package com.urise.webapp;


public class DeadLock {

    public static void main(String[] args) {
        final String object1 = "object1";
        final String object2 = "object2";
        new Thread(() -> lock(object1, object2)).start();
        new Thread(() -> lock(object2, object1)).start();
    }

    private static void lock(Object object1, Object object2) {
        String name = Thread.currentThread().getName();
        synchronized (object1) {
            System.out.println(name+ " captured " + object1);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " waiting to be captured " + object2);
            synchronized (object2) {
                System.out.println("DeadLock is dead");
            }
        }
    }
}


