package com.lsqingfeng.action.knowledge.algorithm.leetcode.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 题号 1： 求两数之和
 * 难度： 简单
 * 题目：
 *  给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *  你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * 示例： 给定 nums = [2, 7, 11, 15], target = 9
 *       因为 nums[0] + nums[1] = 2 + 7 = 9
 *       所以返回 [0, 1]
 *
 */
public class _1_TWO_SUM_1 {

    public static void main(String[] args) {
        int [] nums = {2, 7, 11, 15};
        int target = 9;

        System.out.println(Arrays.toString(twoSum(nums,target)));
        System.out.println(Arrays.toString(twoSum2(nums,target)));

    }

    /**
     * 思路： 快排法：
     *  遍历元素和后面的每个元素做比较
     * 复杂度： O(n2)
     * 结果：
     *   执行用时 :69 ms, 在所有 Java 提交中击败了26.61% 的用户
     *   内存消耗 :39.9 MB, 在所有 Java 提交中击败了5.06% 的用户
     */
    public static int[] twoSum(int[] nums, int target) {
        int [] result = new int[2];
        for(int i=0; i<nums.length -1; i++){
            for(int j = i+1; j<nums.length; j++){
                if(nums[i] + nums[j] == target){
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }

    /**
     *  思路： hash映射，利用hashMap:
     *  将数组的索引和数组的值存入hashMap中，key是这个数，value是索引，方便查询
     *  每次存的时候，查一下对应的 target-i 是否在hashMap
     *  中存在，存在则直接返回结果
     *  复杂度： O(n)
     *  结果：
     *    执行用时 :3 ms, 在所有 Java 提交中击败了 87.45% 的用户
     *    内存消耗 :39.8 MB , 在所有 Java 提交中击败了 5.06% 的用户
     */
    public static int[] twoSum2(int[] nums, int target) {
        int [] result = new int[2];
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            int num1 = nums[i]; // 数组中的数
            int num2 = target - num1; //要找的另一个数
            Integer index = map.get(num2);
            if(index != null){
                result[0] = index;
                result[1] = i;
                return result;
            }
            map.put(num1,i);
        }
        return null;
    }

}
