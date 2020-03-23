package com.lsqingfeng.action.knowledge.multithread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
/**
 * CountDownLatch和CyclicBarrier的主要联系和区别如下：
 *  1.闭锁CountDownLatch做减计数，而栅栏CyclicBarrier则是加计数。
 *  2.CountDownLatch是一次性的，CyclicBarrier可以重用。
 *  3.CountDownLatch强调一个线程等多个线程完成某件事情。CyclicBarrier是多个线程互等，等大家都完成。
 *  4.鉴于上面的描述，CyclicBarrier在一些场景中可以替代CountDownLatch实现类似的功能。
 *
 *  第三点很重要： 闭锁是await等待countdown ,  而栅栏里只有await,
 *
 *
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        int num = 10;
        CyclicBarrier barrier = new CyclicBarrier(num, new Runnable() {
            @Override
            public void run() {
                System.out.println("go on together!");
            }
        });
//        CyclicBarrier barrier = new CyclicBarrier(num);
        for (int i = 1; i <= num; i++) {
            new Thread(new CyclicBarrierWorker(i, barrier)).start();
        }
    }
}
class CyclicBarrierWorker implements Runnable {
    private int id;
    private CyclicBarrier barrier;

    public CyclicBarrierWorker(int id, final CyclicBarrier barrier) {
        this.id = id;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(id + " th people wait");
            barrier.await(); // 大家等待最后一个线程到达
            System.out.println(id +"aa");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
