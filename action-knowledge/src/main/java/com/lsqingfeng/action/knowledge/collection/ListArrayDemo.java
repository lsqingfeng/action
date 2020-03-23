package com.lsqingfeng.action.knowledge.collection;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数组与集合的相互转换
 */
public class ListArrayDemo {
    public static void main(String[] args) {
        int []arr = {1,2,3,4,5,6};
        // int[] 转List  boxed: 装箱：将基本类型转成包装类
        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        System.out.println(list);

        // int[] 转 integer[]
        Integer[] arr2 = Arrays.stream(arr).boxed().toArray(Integer[]::new);

        //List<> 转  Integer[]
        Integer[] arr3 = list.toArray(new Integer[0]);

        // List<> 转 int[]
        int[] arr4 = list.stream().mapToInt(Integer::intValue).toArray();

        //Integer[] 转 int[]
        int[]arr5 = Arrays.stream(arr3).mapToInt(Integer::intValue).toArray();

        // Integer[] 转 List<Integer>
        List<Integer> list2 = Arrays.asList(arr2);

    }
}
