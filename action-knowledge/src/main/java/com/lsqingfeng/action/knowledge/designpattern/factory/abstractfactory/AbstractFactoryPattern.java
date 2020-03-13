package com.lsqingfeng.action.knowledge.designpattern.factory.abstractfactory;

/**
 * 抽象工厂模式：
 * 在工厂方法模式中，其实我们有一个潜在的意识。那就是我们生产的都是同一类产品。抽象工厂模式是工厂方法的进一步深化，在这个模式中工厂类不单单可以创建一种产品，而是可以创建一组产品。抽象工厂应该是比较难理解的一个工厂模式了
 *
 * #### 1.3.1 适用场景：
 * - 和工厂方法一样客户端不需要知道它所创建的对象类
 * - 需要一组对象共同完成某种功能，并且可能存在多组对象完成不同功能的情况（同属同一个产品族的产品）
 * - 系统结构稳定，不会频繁增加对象（因为一增加就需要修改原有代码，不符合开闭原则）
 *
 * #### 1.3.2 角色分配
 * 1. 抽象工厂： 是工厂方法模式的核心，与应用程序无关。任何在模式中创建的对象的工厂类必须实现这个接口。
 * 2. 具体工厂： 是实现抽象工厂接口的具体工厂类，包含与应用程序密切相关的逻辑，并且受到应用程序调用以创建某一种产品对象。
 * 3. 抽象产品：工厂方法模式所创建的对象的超类型，也就是产品对象的共同父类或共同拥有的接口。
 * 4. 具体产品：抽象工厂模式所创建的任何产品对象都是某一个具体产品类的实例。在抽象工厂中创建的产品属于同一产品族，这不同于工厂模式中的工厂只创建单一产品，我后面也会详解介绍到。
 *
 * #### 1.3.3 抽象工厂和工厂方法中的工厂区别
 *
 * 抽象工厂是生产一整套产品的（至少要生产两个产品），这些产品必须相互是有关系或有依赖的，而工厂方法的工厂是生产单一的产品的工厂。
 *
 * 我们以吃鸡游戏举例。游戏中有各种枪。假设现在存在AK,M4A1两类枪，每一种枪对应一种子弹。我们现在这样考虑生产AK的工厂可以顺带生产AK子弹，生产M4A1的工厂可以生产其锁对象的子弹。
 */
public class AbstractFactoryPattern {
    public static void main(String[] args){
        Factory factory;
        Gun gun;
        Bullet bullet;

        factory =new AKFac();
        bullet=factory.produceBullet();
        bullet.load();
        gun=factory.produceGun();
        gun.shooting();
    }

}
//产品
interface Gun{
    public void shooting();
}
interface Bullet{
    public void load();
}
class AK implements Gun{
    public void shooting(){
        System.out.println("shooting with AK");
    }
}
class M4A1 implements Gun{
    public void shooting(){
        System.out.println("shooting with M4A1");
    }
}
class AK_Bullet implements Bullet {
    @Override
    public void load() {
        System.out.println("Load bullets with AK");
    }
}
class M4A1_Bullet implements Bullet {
    @Override
    public void load() {
        System.out.println("Load bullets with M4A1");
    }
}
//工厂：
interface Factory{
    public Gun produceGun();
    public Bullet produceBullet();
}

class AKFac implements Factory{
    public Gun produceGun(){
        return new AK();
    }
    public Bullet produceBullet(){
        return new AK_Bullet();
    }
}

class M4A1Fac implements Factory{
    public Gun produceGun(){
        return new M4A1();
    }
    public Bullet produceBullet(){
        return new M4A1_Bullet();
    }
}



