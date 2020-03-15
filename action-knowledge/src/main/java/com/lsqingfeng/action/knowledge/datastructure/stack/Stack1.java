package com.lsqingfeng.action.knowledge.datastructure.stack;

/**
 * 使用java自己定义一个栈：
 *  栈的特点是  先进先出， 压栈将元素放到栈顶，出栈从栈顶取出元素
 *  实现方式1： 数组
 */
public class Stack1<T> {
    T[] data;
    int top;

    public Stack1(){
        new Stack1<T>(20);
    }
    public Stack1(int capacy){
        data = (T[]) new Object [capacy];
        this.top = -1;// 代表没有数据
    }

    public void push(T t) throws Exception {
        if(top >=data.length-1){
            throw new Exception("栈已满");
        }
        data[++top] = t;
    }

    public int size(){
        return top+1;
    }

    public T pop(){
        return data[top--];
    }

    public static void main(String[] args) throws Exception{
        Stack1<String> stack = new Stack1<>(4);
        stack.push("大家好1");
        stack.push("大家好2");
        stack.push("大家好3");
        stack.push("大家好4");
//        stack.push("大家好5");
        System.out.println(stack.size());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.size());
    }


}
