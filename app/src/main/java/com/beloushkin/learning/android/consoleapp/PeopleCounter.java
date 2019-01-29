package com.beloushkin.learning.android.consoleapp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PeopleCounter {

    public final int NUM_OF_PEOPLE = 100;

    private int numOfLevels;
    private int numOfThreads;


    public int getNumOfLevels() {
        return numOfLevels;
    }

    public int getNumOfThreads() {
        return numOfThreads;
    }

    private int mNumOfPeople = 0;

    // lock object
    private Lock lock = new ReentrantLock();


    public PeopleCounter(int levels, int threads) {
        numOfLevels = levels;
        numOfThreads = threads;
    }

    public void count() {

        System.out.println("Initial number of people " + mNumOfPeople);
        // let's take the time of the execution
        long startTime = System.nanoTime();

        ExecutorService service = Executors.newFixedThreadPool(numOfThreads);
        for (int n = 0; n < numOfLevels; n++ ) {
            service.submit(new CounterThread("Level # " + n));
        }

        service.shutdown();

        // if not completed , we will wait for 10 seconds
        try {
            service.awaitTermination(10,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // end time
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        // my jvm does not convert timeunits right way
        double totalExecutionTime = totalTime/1_000_000.0;
        System.out.println("Counted number of people " + mNumOfPeople);
        System.out.println("Total execution time, seconds: " + totalExecutionTime);

    }

    class CounterThread extends Thread {

        public CounterThread(String name) {
            this.setName(name);
        }

        @Override
        public void run() {

            for (int y = 0; y < NUM_OF_PEOPLE; y++) {
                lock.lock();
                // Need to use try/finally because have to unlock in any case !
                try {
                    mNumOfPeople++;
                } finally {
                    lock.unlock();
                }

                //System.out.println(mNumOfPeople + " " + Thread.currentThread().getName());
            }

        }

    }

}
