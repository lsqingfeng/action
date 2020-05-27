package com.lsqingfeng.action.knowledge.algorithm.leetcode.array;

import org.junit.Test;

import java.util.Arrays;

/**
 * 66. 加一
 * 题目：
 * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 * 示例：
 * 输入: [1,2,3]
 * 输出: [1,2,4]
 * 解释: 输入数组表示数字 123
 *
 * 输入: [4,3,2,1]
 * 输出: [4,3,2,2]
 * 解释: 输入数组表示数字 4321。
 *
 * 注意： 末尾是9的情况
 *
 */
public class _66_PlusOne {

    @Test
    public void test(){
        int [] arr  = {8, 9, 1};
        System.out.println(Arrays.toString(plusOne(arr)));

    }

    /**
     * 暴力法： 从后向前遍历，做加1操作看是否等于10
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :37.8 MB, 在所有 Java 提交中击败了5.63%的用户
     */
    public int[] plusOne(int[] digits) {
        int length = digits.length;
        for(int i= digits.length-1;i>=0;i--){
            int num = digits[i];
            boolean re =  ++num == 10;
            if(re){
                digits[i] = 0;
                if(i==0){
                    int arr[] = new int[length +1];
                    arr[0] = 1;
                    //System.arraycopy(digits,0,arr,1,length);
                    return arr;
                }
            }else{
                digits[i] = num++;
                return digits;
            }

        }
        return digits;
    }

}
