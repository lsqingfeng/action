package com.lsqingfeng.action.knowledge.multithread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        Condition c = lock.newCondition();

        new Thread(new Task1(lock,c)).start();
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Task2(lock,c)).start();

        System.out.println("main....over");


    }

    static class Task1 implements Runnable{
        private ReentrantLock lock;
        private Condition c;
        public Task1(ReentrantLock lock,Condition c){
            this.lock = lock;
            this.c = c;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println("task1....start");
                c.await();
                System.out.println("task1....end....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }
    }

    static class Task2 implements Runnable{
        private ReentrantLock lock;
        private Condition c;
        public Task2(ReentrantLock lock,Condition c){
            this.lock = lock;
            this.c = c;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println("task2....start");
                // 解锁
                c.signal();
                System.out.println("task2....end....");
            }finally {
                lock.unlock();
            }
        }
    }

}


