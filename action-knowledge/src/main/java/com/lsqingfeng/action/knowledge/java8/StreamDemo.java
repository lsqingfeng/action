package com.lsqingfeng.action.knowledge.java8;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @className: StreamDemo
 * @description: java8 Stream案例
 * @author: sh.Liu
 * @create: 2020-05-18 11:21
 */
public class StreamDemo {

    /**
     * 数值流： 主要有 IntStream DoubleStream  LongStream
     * 对象流转为数值流：
     *  IntStream intStream = list.stream().mapToInt(Person:: getAge);
     */
    @Test
    public void numStream(){
        // 左闭右开  [1,3)
        IntStream.range(1,3).forEach(System.out::println);
        // 左闭右闭    [1,3]
        IntStream.rangeClosed(1,3).forEach(System.out::println);

        IntStream intStream = IntStream.of(1,3,4,5,6,7);
        // 求和
        int sum = intStream.sum();
        System.out.println("sum=" +sum);

        // 流只能使用一次，不能重复使用
        intStream = IntStream.of(1,3,4,5,6,7);
        int max = intStream.max().getAsInt();
        intStream = IntStream.of(1,3,4,5,6,7);
        int min = intStream.min().getAsInt();
        intStream = IntStream.of(1,3,4,5,6,7);
        double asDouble = intStream.average().getAsDouble();
        System.out.println("max="+max+",min="+min+",avg="+asDouble);
    }

    /**
     * Match用法
     */
    @Test
    public void match(){
        Integer [] arr = {-1, 0, 12, 3};

        // 全部符合返回true
        Boolean re = Stream.of(arr).allMatch( s-> s > 0 );
        System.out.println(re);

        // 有一个符合返回true
        Boolean re2 = Stream.of(arr).anyMatch( s-> s>0);
        System.out.println(re2);

        // 一个都不符合返回true
        Boolean re3 = Stream.of(arr).noneMatch(s -> s <-2);
        System.out.println(re3);
    }

    /**
     * Optional用法
     * 处理空指针异常：
     *      map/flatMap/filter：与Stream中用法类似
     *      get():值存在时返回值，否则抛出一个NoSuchElement异常
     *      isPresent():有值则返回true
     *      ifPresent(Consumer<T> block):会在值存在的时候执行给定的代码块
     *      orElse(T other):值存在时返回值，否则返回一个默认值
     *      orElseGet(Supplier<? extends T> other)：orElse方法的延迟调用版，Supplier
     *      方法只有在Optional对象不含值时才执行调用
     *      orElseThrow(Supplier<? extends X> exceptionSupplier)：与get()类似，不同的是可以自定义异常类型
     *
     */
    @Test
    public void optional(){
        String str = "abc";
        // String str = null;
        String re = Optional.ofNullable(str).map(String::toUpperCase).orElse("NONE");
        System.out.println(re);

        int value = 1234;
        int result = Optional.ofNullable(value).filter(item -> item == 123).get();
        System.out.println(result);
    }



}
