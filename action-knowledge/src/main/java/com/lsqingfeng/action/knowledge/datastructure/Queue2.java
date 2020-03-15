package com.lsqingfeng.action.knowledge.datastructure;

/**
 * 使用链表实现
 */
public class Queue2<T> {

    Node<T> first;
    Node<T> last;
    int size;

    public void put(T data){
        Node<T> node = new Node(data);
        //代表此时队列为空
        if(first == null && last == null){
            first=node;
            last = node;
        }else{
            //队列不为空：
            last.next = node;
            last = node;
        }
        size++;
    }

    /**
     * 从头部获取一个元素
     * @return
     */
    public T pop(){
        if(size == 0){
            return null;
        }
        Node<T> temp = first;
        first = first.next;
        size--;
        return temp.t;
    }


    /**
     * 使用内部类自定义一个节点
     * @param <T>
     */
    class Node<T>{
        /**
         * 代表当前节点
         */
        T t;
        /**
         * 代表下一个节点
         */
        Node next;
        public Node(T t){
            this.t = t;
        }

        public String toString(){
            return t.toString();
        }
    }

    public void printAll(){
        Node temp = first;
        while(temp.next != null){
            System.out.println(temp);
            temp = temp.next;
        }
        System.out.println(temp);
    }

    public static void main(String[] args) {
        Queue2<String> queue2 = new Queue2<>();
        queue2.put("a");
        queue2.put("b");
        queue2.put("c");
        queue2.put("d");
        queue2.printAll();
        System.out.println(queue2.size);
        System.out.println(queue2.pop());
        System.out.println(queue2.pop());
        System.out.println(queue2.size);
        System.out.println(queue2.pop());
        System.out.println(queue2.pop());
        System.out.println(queue2.pop());
        System.out.println(queue2.size);
    }

}
