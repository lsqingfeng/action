package com.lsqingfeng.action.knowledge.designpattern.factory.factorymethod;


/**
 * 工厂方法模式：
 * 工厂方法模式应该是工厂模式家族中使用最多的模式，一般项目中存在最多的就是这个模式。
 *
 * 工厂方法模式是简单工厂模式的进一步深化，在工厂方法模式中，我们不在提供一个统一的工厂类来创建所有的对象，而是针对不同的对象提供不同的工厂。也就是说每个对象都有一个与之对应的工厂。
 * #### 1.2.1 适用场景：
 * - 一个类不知道它所需要的对象的类： 在工厂方法模式中，客户端不需要知道具体产品类的类名，只需要知道它所对应的工厂即可，具体的产品对象由具体工厂类创建。客户端需要知道创建具体产品的工厂类
 * - 一个类通过其子类来指定创建哪个对象： 在工厂方法模式中，对于抽象工厂类只需要提供一个创建产品的接口，而由其子类来确定具体要创建的对象，利用面向对象的多态性和里氏
 * - 将创建对象的任务委托给多个工厂子类中的某一个，客户端在使用时可以无需关心是哪一个工厂子类创建产品子类，需要时再动态指定，可将具体工厂类的类名存储在配置文件或数据库中
 *
 * #### 1.2.2 角色分配：
 * 1. 抽象工厂（Abstract factory）: 是工厂方法模式的核心，与应用程序无关。任何在模式中创建的对象的工厂类必须实现这个接口
 * 2. 具体工厂（Concrete factory): 这是实现抽象工厂接口的具体工厂类，包含于应用长须密切相关的逻辑，并且受到应用程序调用以创建某一种产品对象
 * 3. 抽象产品（Abstract product) : 工厂方法模式所创建的对象的超类型，也就是产品对象的公共父类或共同接口
 * 4. 具体产品（Concrete product): 这个角色实现了抽象产品角色所定义的接口。某具体产品的由专门的具体工厂创建，他们之间往往一一对应。
 */
public class FactoryMethodPattern {
    public static void main(String[] args) {
        MouseFactory dellFac = new DellMouseFactory();
        Mouse dellMouse = dellFac.getMouse();
        dellMouse.sayHi();
    }
}

/**
 * 抽象工厂和具体工厂
 */
interface MouseFactory{
    Mouse getMouse();
}
class DellMouseFactory implements MouseFactory{
    public Mouse getMouse(){
        return new DellMouse();
    }
}
class HpMouseFactory implements MouseFactory{
    public Mouse getMouse(){
        return new HpMouse();
    }
}

/**
 * 抽象产品和具体产品
 */
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

