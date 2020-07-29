package com.lsqingfeng.action.knowledge.java8;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @className: CompletableFutureDemo
 * @description: Completable用法：
 *      实现了CompletionStage接口，该接口也是jdk8中新增的，主要是为了函数式编程中的流式调用准备的。
 *      可以显式完成的Future(设置其值和状态)，并且可以用作CompletionStage, 支持在完成时触发依赖函数和操作。
 *      当两个或多个线程尝试complete, completeExceptionally 或 cancel CompletableFuture时， 只有一个能成功
 *
 * @author: sh.Liu
 * @date: 2020-07-17 09:10
 */
public class CompletableFutureDemo {

    /**
     * Future是从jdk5新增的接口，主要用于异步处理，实现一个可无需等待被调用函数的返回值，而让操作继续运行的方法。
     * 用于描述一个异步计算的结果。比如：
     */
    @Test
    public void test(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(()->{
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return 1;
        });

        try {
            System.out.println(future.get());
            System.out.println("finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Future接口的局限性：
     *  Future接口可以构建异步应用，但是依然有局限性。 它很难直接表述多个Future之间结果的依赖性。
     *  实际开发中，我们经常需要达成以下目的：
     *  1. 将多个计算结果合并成一个
     *  2. 等待Future集合中的所有任务都完成
     *  3. Future完成事件（即任务完成后触发执行动作）
     *
     *  CompletionStage: 代表异步计算过程中某一个阶段，一个阶段完成以后可能会出发另外一个阶段
     *  一个阶段的计算执行，可以是一个Function, Consumer或者Runnable. 比如
     *  state.thenApply(x -> square(x)).thenAccept(System.out::println).thenRun(b-> System.out.println(b));
     *
     *  在java8中，CompleteFuture提供了非常强大的Future扩展功能，可以帮助我们简化异步编程的负载性，提供了函数式编程的能力，
     *  可以通过回调的方式处理计算结果，也提供了转换和组合CompletablFuture的方法
     *  它可能代表一个明确完成的Future, 也可能代表一个完成阶段（CompletionStage), 它支持在计算完成之后触发一些含糊或者执行
     *  某些动作， 它实现了Future和 CompletionStage
     */
    @Test
    public void test2(){
        // 均为异步处理
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> getNum(100))
                .thenApply(s -> s + 1)
                .whenComplete((r, e) -> System.out.println(r));

        /**
         * 为什么不加下面的代码，就无法打印出结果？？
         * 因为我们在调用supplyAsync 方法的时候没有指定线程池，没有指定线程池，那么会默认使用ForkJoinPool.commonPool()方法
         * 它可以获得一个公共的ForkJoin线程池，这个公共线程池中的所有线程都是Daemon线程，这意味着如果主线程退出，这些线程无论是否执行完毕都会退出
         * 所以我们要保证主线程不能退出，才能得出结果
         */

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        try {
//            System.out.println(integerCompletableFuture.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }


    }

    public int getNum(Integer i){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i*i;

    }



}
