package com.lsqingfeng.action.knowledge.datastructure.queue;

import java.util.LinkedList;

/**
 * 使用java自己实现一个队列：
 * 队列的特点； 先进先出
 * 实现方式：
 *      1. 使用LinkedList实现
 *      2. 使用自定义链表实现
 */
public class Queue1<T> {
    LinkedList<T> queue = new LinkedList<>();
    int size;

    public void put(T t){
        queue.add(t);
        size++;
    }

    public T pop(){
        size --;
        return queue.removeFirst();
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

}
