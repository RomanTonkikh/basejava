package com.urise.webapp;

import com.urise.webapp.util.LazySingleton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10_000;
//    private static int counter;
    private final AtomicInteger atomicInteger = new AtomicInteger();
//    private static final Lock lock = new ReentrantLock();
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };

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

        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
//        List<Thread> threads = new ArrayList<>(TREADS_NUMBER);
        final ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService completionService = new ExecutorCompletionService(executorService);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            executorService.submit(() -> {
                        for (int j = 0; j < 100; j++) {
                            mainConcurrency.inc();
                            System.out.println(threadLocal.get().format(new Date()));
                        }
                        latch.countDown();
                       });
//                 Thread thread = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    mainConcurrency.inc();
//                }
//                latch.countDown();
//            });
//            thread.start();
//            threads.add(thread);
               }

        /*threads.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/

        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {

        }
        executorService.shutdown();
//        System.out.println(counter);
        System.out.println(mainConcurrency.atomicInteger.get());
        final LazySingleton instance = LazySingleton.getInstance();
    }

    private void inc() {
//      synchronized (this) {
//      synchronized (MainConcurrency.class) {
//        lock.lock();
        atomicInteger.incrementAndGet();
//        try {
//            counter++;
//        } finally {
//            lock.unlock();
//        }
    }
}




