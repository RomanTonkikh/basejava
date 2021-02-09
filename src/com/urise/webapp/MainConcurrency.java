package com.urise.webapp;

import com.urise.webapp.util.LazySingleton;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int TREADS_NUMBER = 10_000;
    private static int counter;

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ",   " + getState());
            }
        };
        thread0.start();
        new Thread(() -> System.out.println(Thread.currentThread().getName() + ",   " + Thread.currentThread().getState())).start();
        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(TREADS_NUMBER);

        for (int i = 0; i < TREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
               }

        threads.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);
        final LazySingleton instance = LazySingleton.getInstance();
    }

    private synchronized void inc() {
//      synchronized (this) {
//      synchronized (MainConcurrency.class) {
        counter++;
    }
}




