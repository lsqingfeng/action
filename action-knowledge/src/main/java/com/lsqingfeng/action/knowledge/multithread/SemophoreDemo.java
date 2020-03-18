package com.lsqingfeng.action.knowledge.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量：可用于限流：
 *
 *
 */
public class SemophoreDemo {
    private static final int THREAD_COUNT = 40;
    private static ExecutorService threadPool = Executors
            .newFixedThreadPool(THREAD_COUNT);
    private static Semaphore s = new Semaphore(10,true);

    /**
     * 程序共有40个线程，但信号量中只有10个，true代表公平锁：
     * 每次只能有10个线程可以向下执行，直到有新的许可释放
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程" + Thread.currentThread().getName() + " 读取文件");
                        s.acquire();
                        System.out.println("线程---------" + Thread.currentThread().getName() + " 存储文件");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        s.release();
                    }
                }
            });
        }
        threadPool.shutdown();
    }
}
