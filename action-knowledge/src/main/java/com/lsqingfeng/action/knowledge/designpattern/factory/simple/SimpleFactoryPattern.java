package com.lsqingfeng.action.knowledge.designpattern.factory.simple;

/**
 * 工厂模式： GOF定义：“Define an interface for creating an object, but let subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.”(在基类中定义创建对象的一个接口，让子类决定实例化哪个类。工厂方法让一个类的实例化延迟到子类中进行。)
 * **属于创建型模式**
 * 广义上的工厂模式其实分为三种：分别是简单工厂模式（simple factory），工厂模式(工厂方法factory method)，抽象工厂模式(abstract factory)。
 * 为什么要使用工厂模式：
 * 1. 解耦： 把对象的创建和使用过程分开
 * 2. 降低代码重复：如果创建某个对象的过程都很复杂，需要一定的代码量，而且很多地方都要用到，那么就会有很多重复的代码
 * 3. 降低维护成本：由于创建过程都由工厂统一管理，所以发生业务逻辑变化，不需要找到所有需要创建某个对象的地方逐个修正，只需要在工厂里修改即可，降低维护成本
 *
 * 简单工厂模式：简单工厂模式不是23种设计模式里的一种，简而言之，就是有一个专门生产某个产品的类。
 *
 */
public class SimpleFactoryPattern {
    public static void main(String[] args) {
        // 使用工厂模式创建对象
        Mouse m = MouseFacotry.createMouse(1);
        m.sayHi();
    }

}
interface Mouse{
    void sayHi();
}
class DellMouse implements Mouse{
    public void sayHi(){
        System.out.println("hi,DellMouse");
    }
}
class HpMouse implements Mouse{
    public void sayHi(){
        System.out.println("hi,HpMouse");
    }
}

class MouseFacotry{
    public static Mouse createMouse(int i){
        switch(i){
            case 0:return new DellMouse();
            case 1:return new HpMouse();
            default:return null;
        }
    }
}
