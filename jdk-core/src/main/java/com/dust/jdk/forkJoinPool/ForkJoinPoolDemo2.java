package com.dust.jdk.forkJoinPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * @author DUST
 * @description ForkJoinPool结合（求1到n个数的和）操作
 * @date 2021/12/21
 */
public class ForkJoinPoolDemo2 {
    /**
     * 快排有2个步骤：
     * 1. 利用数组的第1个元素把数组划分成两半，左边数组里面的元素小于或等于该元素，右边数组里面的元素比该元素大；
     * 2. 对左右的两个子数组分别排序。
    */

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int value = 1 << 10;
        System.out.println(value);
        long startTime = System.currentTimeMillis();

        //一个任务
        SumTask sum = new SumTask(value);
        //一个pool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //forkJoinPool开启多个线程，同时执行上面的任务
        ForkJoinTask<Long> future = forkJoinPool.submit(sum);
        //打印结果
        System.out.println(future.get());
        //结束forkJoinPool
        forkJoinPool.shutdown();

        long endTime = System.currentTimeMillis();

        System.out.println("forkJoinPool执行时间："+(endTime-startTime)+"ms");

        startTime = System.currentTimeMillis();

        long result = 0;
        for (long l = 0; l <= value; l++) {
            result += l;
        }
        System.out.println(result);
        endTime = System.currentTimeMillis();
        System.out.println("普通执行时间："+(endTime-startTime)+"ms");
    }

    public static class SumTask extends RecursiveTask<Long> {

        public static final int THRESHOLD = 10;
        private long start;
        private long end;

        public SumTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public SumTask(long n) {
            this(1,n);
        }


        @Override
        protected Long compute() {
            long sum = 0;
            //如果计算的范围在threshold之内，则直接进行计算
            if((end-start) <= THRESHOLD){
                for (long l = start; l <= end; l++) {
                    sum += l;
                }
            }else{
                //否则找出开始和结束的中间值，分割任务
                long mid = (start+end) >>> 1;
                SumTask left = new SumTask(start, mid);
                SumTask right = new SumTask(mid+1, end);
                left.fork();
                right.fork();
                //收集子任务计算结果
                sum = left.join() + right.join();
            }
            return sum;
        }
    }
}
