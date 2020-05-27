package com.lsqingfeng.action.knowledge.algorithm;

/**
 * @className: Test
 * @description:
 * @author: sh.Liu
 * @create: 2020-05-08 22:00
 */
public class Test {

    @org.junit.Test
    public void test(){
        System.out.println(maxWidth(new int[]{6,0,8,2,1,1}));

        System.out.println(minDeletionSize(new String[]{"xcd","abe","zaf"}));
    }

    public int maxWidth(int[] a){
        for(int i = 0;i<a.length;i++){
            int j = a.length-1;
            if(i>=j){
                return 0;
            }

            int num1 = a[i];
            int num2 = a[j];
            if(num1 <= num2){
                return j - i;
            }else{
                num1 = a[i+1];
                num2 = a[j];
                if(num1 <= num2){
                    return j - i -1;
                }else{
                    num1 = a[i];
                    num2 = a[j-1];
                    if(num1 <= num2){
                        return j-1 -i;
                    }
                }

            }

        }
        return 0;
    }



    public int minDeletionSize(String[] A){

        // [xc yb za]
        String s = A[0];

        for(int i = 0;i < s.toCharArray().length;i++){
            for(int j = 0;j<A.length-1;j++){
                int k = j+1;

                char c1 = A[j].charAt(i);
                char c2 = A[k].charAt(i);
                if(c1 > c2){
                    break;
                }
                return i;
            }
        }

        return -1;
    }

}
