package com.lsqingfeng.action.knowledge.designpattern.singleton;

/**
 * 使用静态内部类实现
 */
public class Singleton2 {
    private  Singleton2(){}

    private static class SingletonHoleder{
        private static final Singleton2 INSTANCE = new Singleton2();
    }

    public static Singleton2 getInstance(){
        return SingletonHoleder.INSTANCE;
    }

}
