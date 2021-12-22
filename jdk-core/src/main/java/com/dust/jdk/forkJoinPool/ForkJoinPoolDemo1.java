package com.dust.jdk.forkJoinPool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * @author DUST
 * @description ForkJoinPool结合快排操作
 * @date 2021/12/21
 */
public class ForkJoinPoolDemo1 {
    /**
     * 快排有2个步骤：
     * 1. 利用数组的第1个元素把数组划分成两半，左边数组里面的元素小于或等于该元素，右边数组里面的元素比该元素大；
     * 2. 对左右的两个子数组分别排序。
    */

    public static void main(String[] args) throws InterruptedException {
        long[] array = {5,3,7,9,2,4,1,8,10};
        //一个任务
        ForkJoinTask sortTask = new SortTask(array);
        //一个pool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //forkJoinPool开启多个线程，同时执行上面的任务
        forkJoinPool.submit(sortTask);
        //结束forkJoinPool
        forkJoinPool.shutdown();
        //等待结束pool
        forkJoinPool.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println(Arrays.toString(array));
    }

    public static class SortTask extends RecursiveAction{

        final long[] array;
        final int lo;
        final int hi;

        public SortTask(long[] array, int lo, int hi) {
            this.array = array;
            this.lo = lo;
            this.hi = hi;
        }

        public SortTask(long[] array) {
            this.array = array;
            this.lo = 0;
            this.hi = array.length - 1;
        }

        private int partition(long[] array, int left, int right) {
            //比较的元素
            long key = array[left];
            //左边的下标
            int i = left;
            //右边的下标
            int j = right;
            //遍历让左右的元素交换直至左边的元素都小于右边的元素
            while(i < j){
                //j向左移动，直至遇到比key小的值
                while(array[j] >= key && i < j){
                    j--;
                }
                //i向左移动，直至遇到比key大或者等于的值
                while(array[i] <= key && i < j){
                    i++;
                }
                if(i<j) {
                    swap(array, i, j);
                }
            }
            //把比较的元素换到i的位置
            array[left] = array[i];
            array[i] = key;
            return i;
        }

        private void swap(long[] array, int i, int j){
            if(i != j){
                long temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        @Override
        protected void compute() {
            if(lo < hi){
                //找到分区的元素下标
                int pivot = partition(array,lo,hi);
                //将数组分为两部分
                SortTask left = new SortTask(array,lo,pivot-1);
                SortTask right = new SortTask(array,pivot+1,hi);

                left.fork();
                right.fork();
                left.join();
                right.join();
            }

        }
    }
}
