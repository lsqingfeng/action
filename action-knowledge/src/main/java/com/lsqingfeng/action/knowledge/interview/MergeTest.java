package com.lsqingfeng.action.knowledge.interview;

/**
 * 题目： 一个升序数组，一个降序数组，给定一个数n, 获取topN
 */
public class MergeTest {

    public static void main(String[] args) {
        int [] a = {1,2,3,4,5};

        int [] b = {9,8,7,6,5};
        // 求两个数组中第四大的数
        int n = 4;

        int [] arr = new int[a.length + b.length];
        int r = 0;

        for(int i=0;i<a.length;i++){
            int j = 0;
            int num1 = a[i];
            int num2 = b[j];

            if(num1 < num2){
                arr[r] = num2;

            }else{

            }


        }






    }




}
