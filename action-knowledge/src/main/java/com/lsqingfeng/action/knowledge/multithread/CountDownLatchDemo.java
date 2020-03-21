package com.lsqingfeng.action.knowledge.multithread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch: 闭锁，一种同步方法，可以延迟线程的进度，直到线程达到某个重点状态
 *      CountDownLatch内部会维护一个初始值为线程数量的计数器，主线程执行await方法，
 *      如果计数器大于0，则阻塞等待。当一个线程完成任务后，计数器值减1。当计数器为0时，表示所有的线程已经完成任务，
 *      等待的主线程被唤醒继续执行。
 *  类似于王者荣耀，大家都准备好了，才继续往下
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(5);
        Thread t1 = new Thread(new Task("【亚瑟】加载完毕",latch));
        Thread t2 = new Thread(new Task("【后羿】加载完毕",latch));
        Thread t3 = new Thread(new Task("【安其拉】加载完毕",latch));
        Thread t4 = new Thread(new Task("【兰陵王】加载完毕",latch));
        Thread t5 = new Thread(new Task("【夏侯惇】加载完毕",latch));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        latch.await();

        System.out.println("所有玩家准备完毕，游戏开始");
    }

}

class Task implements Runnable{

    private String str;
    private  CountDownLatch latch;

    public Task(String str,CountDownLatch latch){
        this.str = str;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(str);
        latch.countDown();
    }
}


