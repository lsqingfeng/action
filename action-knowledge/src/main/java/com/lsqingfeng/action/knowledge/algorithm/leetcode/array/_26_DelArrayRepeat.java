package com.lsqingfeng.action.knowledge.algorithm.leetcode.array;

import org.junit.Test;

import java.util.Arrays;

/**
 * 题号：26. 删除排序数组中的重复项
 * 难度：简单
 * 题目：给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
 *  不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 *
 *
 */
public class _26_DelArrayRepeat {

    @Test
    public void test(){
        int[] arr = {1, 1, 1, 2};
        System.out.println(removeDuplicates(arr));
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 思路：
     *  由于数组已经是有序的，所以我们可以采用双指针的方式，两两比较，
     *  数字不同时，长度再加1
     */
    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        if(len <= 1){
            return len;
        }

        int i=0;
        for(int j=1;j<len;j++){
            if(nums[i] != nums[j]){
                nums[++i] = nums[j];
            }
        }
        return i+1;
    }

}
