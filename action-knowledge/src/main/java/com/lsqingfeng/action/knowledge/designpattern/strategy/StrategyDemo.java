package com.lsqingfeng.action.knowledge.designpattern.strategy;

/**
 * 策略模式：
 *  定义一组算法，将每个算法都封装起来，并且试他们之间可以互换
 * 角色：
 *  1. Context: 上下文引用，持有一个策略引用
 *  2. 抽象策略： 这是一个抽象角色，通常由一个接口或抽象类实现。此角色给出所有的具体策略所需的接口。经常见到的是，所有的具体策略类都有一些公有的行为，这时，就应该把这些公有的行文放到公共的抽象策略角色Strategy类里面。
 *  3. 具体策略：定义具体的行为或算法
 */
public class StrategyDemo {

    public static void main(String[] args) {
        Context c = new Context(new OperationAdd());
        System.out.println(c.executeStrategy(1,2));

        c = new Context(new OperationDiv());
        System.out.println(c.executeStrategy(14,2 ));
    }
}
interface Strategy{
    int opreator(int num1,int num2);
}
class OperationAdd implements Strategy{
    @Override
    public int opreator(int num1, int num2) {
        return num1 + num2;
    }
}
class OperationMulti implements Strategy{
    @Override
    public int opreator(int num1, int num2) {
        return num1 * num2;
    }
}
class OperationSub implements Strategy{
    @Override
    public int opreator(int num1, int num2) {
        return num1 - num2;
    }
}
class OperationDiv implements Strategy{
    @Override
    public int opreator(int num1, int num2) {
        return num1 / num2;
    }
}
class Context{
    private Strategy strategy;
    public Context(Strategy strategy){
        this.strategy = strategy;
    }
    public int executeStrategy(int num1,int num2){
        return strategy.opreator(num1,num2);
    }
}



