package com.lsqingfeng.action.knowledge.collection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *  juc下线程安全的集合类总结：
 *
 *  CopyOnWriteArrayList   相当于   ArrayList: 使用Object[] 和  ReentrantLock 保证线程安全
 *  CopyOnWriteArraySet 相当于  HashSet:  使用CopyOnWriteArrayList 实现 add调用addIfAbsend : 不存在则添加
 *  ConcurrentHashMap: 相当于HashMap， 线程安全的map:
 *  ConcurrentSkipListMap： 线程安全，相当于 TreeMap, 使用跳表实现
 *  ConcurrentSkipListSet:  线程安全，相当于TreeSet
            */
        public class SafeCollectionDemo {
            public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("aaaa");
        list.add("bbbb");
        list.add("cccc");
        list.add("dddd");
        System.out.println(list);

        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
        set.add("123");
        set.add("123");
        set.add("456");
        set.add("678");
        System.out.println(set); //[123, 456, 678]

        ConcurrentHashMap<Integer,String> map = new ConcurrentHashMap<Integer, String>();
        map.put(1,"A");
        map.put(2,"b");
        map.put(1,"c");
        System.out.println(map);

        ConcurrentSkipListMap<Integer,String> map2 = new ConcurrentSkipListMap<>();
        map2.put(4,"ssd");
        map2.put(6,"asd");
        map2.put(2,"nfd");
        System.out.println(map2);


    }
}
