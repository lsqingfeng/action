package com.lsqingfeng.action.knowledge.algorithm;


/**
 * @className: Test2
 * @description:
 * @author: sh.Liu
 * @create: 2020-05-08 22:59
 */
public class Test2 {


    class TreeNode{
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int _val){
            val = _val;
        }
    }


    public int test(TreeNode root){
        int count = 0;
        TreeNode left = root.left;
        TreeNode right = root.right;

        if((left.left == null && left.right==null) || (right.left == null && right.right==null)){
            count = getCount(left.left) +getCount(left.right) +getCount(right.left) +getCount(right.right);
        }

        //递归调用
        count = test(root.left);
        return count;
    }

    public int getCount(TreeNode node){
        return node == null ? 0: node.val;
    }

}
