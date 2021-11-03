package com.dust.jdk.syncTool.cyclicBarrier;

import com.dust.jdk.syncTool.countDownLatch.CountDownLatchDemo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author DUST
 * @description CyclicBarrier使用演示说明
 * @date 2021/11/3
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws InterruptedException {
        //使用场景：5个工程师一起来公司应聘，招聘方式分为笔试和面试。首先，要等人到齐后，开始笔试
        //笔试结束之后，再一起参加面试。把5个人看作5个线程
        //在整个过程中，有2个同步点：第1个同步点，要等所有应聘者都到达公司，再一起开始笔试；
        //第2个同步点，要等所有应聘者都结束笔试，之后一起进入面试环节。

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        //模拟五个工程师来应聘
        new MyThread("1号工程师",cyclicBarrier).start();
        new MyThread("2号工程师",cyclicBarrier).start();
        new MyThread("3号工程师",cyclicBarrier).start();
        new MyThread("4号工程师",cyclicBarrier).start();
        new MyThread("5号工程师",cyclicBarrier).start();
    }

    private static class MyThread extends Thread{

        private final CyclicBarrier cyclicBarrier;

        private MyThread(String name,CyclicBarrier cyclicBarrier) {
            super(name);
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                //到公司的路上
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+"--已经到达公司");
                //暂停等待所有人到齐
                cyclicBarrier.await();

                //笔试中
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+"--已经笔试结束");
                //暂停等待所有人笔试结束
                cyclicBarrier.await();

                //面试中
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+"--已经面试结束");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
