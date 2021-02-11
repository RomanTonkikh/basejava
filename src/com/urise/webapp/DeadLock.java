package com.urise.webapp;

import java.util.Objects;

public class DeadLock {

    public static void main(String[] args) {
        final DeadLock dl1 = new DeadLock();
        final DeadLock dl2 = new DeadLock();
        new Thread(() -> dl1.lock(dl1, dl2)).start();
        new Thread(() -> dl2.lock(dl2, dl1)).start();
    }

    private void lock(DeadLock dl1, DeadLock dl2) {
        synchronized (Objects.requireNonNull(dl1)) {
            System.out.println("Объект " + dl1 + " используется");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Попытка обращения к объекту " + dl2);
            synchronized (Objects.requireNonNull(dl2)) {
                System.out.println("DeadLock не произошел");
            }
        }
    }
}


