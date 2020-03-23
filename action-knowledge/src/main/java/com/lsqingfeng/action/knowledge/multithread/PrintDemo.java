package com.lsqingfeng.action.knowledge.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实例化三个线程，一个线程打印a，一个线程打印b，一个线程打印c，三个线程同时执行，要求打印出10个连着的abc
 */
public class PrintDemo implements Runnable{

    // 打印次数
    private static final int PRINT_COUNT = 10;
    // 打印锁
    private final ReentrantLock reentrantLock;
    // 本线程打印所需的condition
    private final Condition thisCondtion;
    // 下一个线程打印所需要的condition
    private final Condition nextCondtion;
    // 打印字符
    private final char printChar;

    public PrintDemo(ReentrantLock reentrantLock, Condition thisCondtion, Condition nextCondition,
                         char printChar) {
        this.reentrantLock = reentrantLock;
        this.nextCondtion = nextCondition;
        this.thisCondtion = thisCondtion;
        this.printChar = printChar;
    }

    @Override
    public void run() {
        // 获取打印锁 进入临界区
        reentrantLock.lock();
        try {
            // 连续打印PRINT_COUNT次
            for (int i = 0; i < PRINT_COUNT; i++) {
                //打印字符
                System.out.print(printChar);
                // 使用nextCondition唤醒下一个线程
                // 因为只有一个线程在等待，所以signal或者signalAll都可以
                nextCondtion.signal();
                // 不是最后一次则通过thisCondtion等待被唤醒
                // 必须要加判断，不然虽然能够打印10次，但10次后就会直接死锁
                if (i < PRINT_COUNT - 1) {
                    try {
                        // 本线程让出锁并等待唤醒
                        thisCondtion.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        } finally {
            // 释放打印锁
            reentrantLock.unlock();
        }
    }


    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();

        Thread threadA = new Thread(new PrintDemo(lock,conditionA,conditionB,'A'));
        Thread threadB = new Thread(new PrintDemo(lock,conditionB,conditionC,'B'));
        Thread threadC = new Thread(new PrintDemo(lock,conditionC,conditionA,'C'));


        threadA.start();
        threadB.start();
        threadC.start();
    }


}
