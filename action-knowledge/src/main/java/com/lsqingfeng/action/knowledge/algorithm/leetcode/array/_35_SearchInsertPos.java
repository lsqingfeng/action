package com.lsqingfeng.action.knowledge.algorithm.leetcode.array;

import org.junit.Test;

/**
 * 题号： 35. 搜索插入位置
 * 难度： 简单
 * 题目：
 *  给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，
 *  返回它将会被按顺序插入的位置。你可以假设数组中无重复元素。
 * 示例：
 *  1.  输入: [1,3,5,6], 5
 *      输出: 2
 *  2.  输入: [1,3,5,6], 2
 *      输出: 1
 *  3. 输入: [1,3,5,6], 7
 *      输出: 4
 *  4. 输入: [1,3,5,6], 0
 *      输出: 0
 */
public class _35_SearchInsertPos {

    @Test
    public void test(){
        System.out.println(searchInsert(new int[]{1,2,3,4,5,7}, 0));
    }

    /**
     * 1. 暴力法，一个个遍历
     * 时间复杂度 O(n)
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00% 的用户
     * 内存消耗 :39.6 MB, 在所有 Java 提交中击败了5.55%的用户
     */
    public int searchInsert(int[] nums, int target) {
        if(nums.length <=0){
            return -1;
        }
        if(target < nums[0]){
            return 0;
        }
        if(target > nums[nums.length - 1]){
            return nums.length;
        }
        for (int i=0; i<nums.length; i++ ) {
            if(target == nums[i]){
                return i;
            }
            if(target > nums[i] && target < nums[i+1]){
                return i+1;
            }
        }
        return 0;
    }

}
