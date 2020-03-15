package com.lsqingfeng.action.knowledge.datastructure.stack;

import lombok.Data;

/**
 * 使用链表实现:
 */
public class Stack2<T> {

    Node<T> top;

    public void push(T t){
        Node<T> node = new Node(t);
        node.next = top;
        this.top = node;
    }

    public Node<T> pop(){
        Node temp = top;
        top = top.next;
        return temp;
    }


    /**
     * 使用内部类自定义一个节点
     * @param <T>
     */
    @Data
    class Node<T>{
        /**
         * 代表当前节点
         */
        T t;
        /**
         * 代表下一个节点
         */
        Node<T> next;
        public Node(T t){
            this.t = t;
        }

        public String toString(){
            return t.toString();
        }
    }

    public static void main(String[] args) {
        Stack2<String> stack2 = new Stack2<>();
        stack2.push("a");
        stack2.push("b");
        stack2.push("c");
        stack2.push("d");

        System.out.println(stack2.top);

        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
    }

}
