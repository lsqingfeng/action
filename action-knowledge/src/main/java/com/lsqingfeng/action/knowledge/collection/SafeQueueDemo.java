package com.lsqingfeng.action.knowledge.collection;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 队列用法：
 * ArrayBlockingQueue是数组实现的线程安全的有界的阻塞队列
 * LinkedBlockingQueue是单向链表实现的(指定大小)阻塞队列，该队列按 FIFO（先进先出）排序元素。
 * LinkedBlockingDeque是双向链表实现的(指定大小)双向并发阻塞队列，该阻塞队列同时支持FIFO和FILO两种操作方式。
 * ConcurrentLinkedQueue是单向链表实现的无界队列，该队列按 FIFO（先进先出）排序元素。
 * ConcurrentLinkedDeque是双向链表实现的无界队列，该队列同时支持FIFO和FILO两种操作方式
 * 核心方法介绍：
 *  boolean add(E)	入队。若满了，抛异常
 *  boolean offer(E)	入队。若满了，返回false
 *  boolean offer(E, timeout)	入队。若满了，则等待，超时返回false
 *  void put(E)	入队。若满了，则等待
 *
 *  E take()	获取并出队。若空，则等待
 *  E poll()	获取并出队。若空，则等待一定时间，超时后返回null
 *  E remove()	获取并出队。若空，则抛异常
 *
 *  E element()	获取队头，不出队。若空，则抛异常
 *  E peek()	获取队头，不出队。若空，则返回null
 *
 */
public class SafeQueueDemo {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(1);
        arrayBlockingQueue.add("Abc");
//        arrayBlockingQueue.add("def");// java.lang.IllegalStateException: Queue full
        boolean re = arrayBlockingQueue.offer("def");// 队满返回false
        System.out.println(arrayBlockingQueue);
        String poll = arrayBlockingQueue.poll();
        System.out.println(poll);
        arrayBlockingQueue.add("def");
        System.out.println(arrayBlockingQueue);




    }
}
