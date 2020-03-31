package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws Exception {
        Resource resource = new Resource();
        resource.i = 5;
        resource.j = 5;
        MyThread myThread = new MyThread();
        myThread.setName("one");
        MyThread myThread1 = new MyThread();
        myThread.resource = resource;
        myThread1.resource = resource;
        myThread.start();
        myThread1.start();
        myThread.join();
        myThread1.join();
        System.out.println(resource.i);
        System.out.println(resource.j);
    }

    static class MyThread extends Thread {
        Resource resource;

        @Override
        public void run() {
            resource.changeI();
        }
    }
}

class Resource {
    int i;
    int j;

    Lock lock = new ReentrantLock();

    void changeI() {
        lock.lock();
        int i = this.i;
        if (Thread.currentThread().getName().equals("one")) {
            Thread.yield();
        }
        i++;
        this.i = i;
        changeJ();
    }

    void changeJ() {
        int j = this.j;
        if (Thread.currentThread().getName().equals("one")) {
            Thread.yield();
        }
        j++;
        this.j = j;
        lock.unlock();
    }
}