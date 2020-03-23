package com.lsqingfeng.action.knowledge.designpattern.singleton;

/**
 * 单例设计模式： 懒汉式
 */
public class Singleton {
    private Singleton(){}
    //加volatile是因为  new也不是线程安全的，可能会因为指令重排出现问题
    public static volatile Singleton s;
    public Singleton getInstance(){
        if(s == null){
            synchronized (Singleton.class){
                if(s == null){
                    s = new Singleton();
                }
            }
        }
        return s;
    }

}
