package com.lsqingfeng.action.knowledge.multithread;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentranctLock： 是独占锁，即只允许一个线程访问，但有时候，我们的业务场景中大多数以读为主。这时候如果使用独占锁，
 * 性能会比较低，这时候我们可以用选择ReentrantReanWriteLock: 读写锁允许多个读线程同时访问，但是多个写线程或者读写线程
 * 都是互斥的。
 *  也就是这个锁，提供了读锁和写锁，可以，更细粒度的控制读写，使用读锁，允许多个线程同时访问
 *
 * 通过案例运行结果可以看出来，读并行，写串行
 */
public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        for(int i=0;i<100;i++) {
            new Thread(()->myTask.getNum()).start();
        }
        System.out.println("end-----------------");
        for(int i=0;i<100;i++) {
            new Thread(()->myTask.setNum(new Random().nextInt())).start();
        }

    }

}

class MyTask{

    int num = 100;

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 读锁，允许同时读
    public void getNum(){
        try{
            readWriteLock.readLock().lock();
            System.out.println("READ"+Thread.currentThread().getName()+" : "+ num);
            Thread.sleep(10);
            System.out.println("READ"+Thread.currentThread().getName() + ":  end");
        }catch (Exception e){

        }finally{
            readWriteLock.readLock().unlock();
        }
    }

    //写的时候用写锁，互斥
    public void setNum(int num){
        readWriteLock.writeLock().lock();
        try {
            this.num = num;
            System.out.println("WRITE"+Thread.currentThread().getName()+" : "+num);
            Thread.sleep(1000);
            System.out.println("WRITE"+Thread.currentThread().getName() + ":  end");
        }catch (Exception e){

        }finally {
            readWriteLock.writeLock().unlock();
        }
    }



}
