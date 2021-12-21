package com.dust.jdk.syncTool.phaser;

import java.util.concurrent.Phaser;

/**
 * @author DUST
 * @description phaser demo {@link #repalceCountDownLatch()}  {@link #repalceCyclicBarrier()}
 *                 从JDK7开始，新增了一个同步工具类Phaser，其功能比CyclicBarrier和CountDownLatch更加强大
 * @date 2021/12/7
 */
public class PhaserDemo {

    public static void main(String[] args) {
        new PhaserDemo().repalceCountDownLatch();
        //new PhaserDemo().repalceCyclicBarrier();
    }


    /**
     * @author DUST
     * @description 用Phaser替代CountDownLatch
     * @date 2021/12/7
     * @param
     * @return void
    */
    public void repalceCountDownLatch(){
        /**
         * 用CountDownLatch时，1个主线程要等N个worker线程完成之后，才能做接下来的事
         * 情，也可以用Phaser来实现此功能。在CountDownLatch中，主要是2个方法：await()和
         * countDown()，在Phaser中，与之相对应的方法是awaitAdance(int n)和arrive()。
        */

        int count = 5;

        Phaser phaser = new Phaser(count);

        System.out.println("程序开始启动");
        for (int i = 0; i < count; i++) {
            new CountDownLatchThread("线程"+(i+1),phaser).start();
        }

        phaser.awaitAdvance(phaser.getPhase());

        System.out.println("程序运行结束");
    }

    /**
     * @author DUST
     * @description 用Phaser替代CyclicBarrier
     * @date 2021/12/7
     * @param
     * @return void
    */
    public void repalceCyclicBarrier(){
        /**
         * arriveAndAwaitAdance()就是 arrive()与 awaitAdvance(int)的组合，表示“我自己已到达这个同步
         * 点，同时要等待所有人都到达这个同步点，然后再一起前行”。
        */

        int count = 5;

        Phaser phaser = new Phaser(5);


        for (int i = 0; i < count; i++) {
            new CyclicBarrierThread("线程"+(i+1),phaser).start();
        }

        phaser.awaitAdvance(0);
    }


    public class CyclicBarrierThread extends Thread{

        private Phaser phaser;

        public CyclicBarrierThread(String name,Phaser phaser) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                //到公司的路上
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+"--已经到达公司");
                //暂停等待所有人到齐
                phaser.arriveAndAwaitAdvance();

                //笔试中
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+"--已经笔试结束");
                //暂停等待所有人笔试结束
                phaser.arriveAndAwaitAdvance();

                //面试中
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+"--已经面试结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class CountDownLatchThread extends Thread{

        private Phaser phaser;

        public CountDownLatchThread(String name,Phaser phaser) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+"开始运行");
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+"运行结束");
                phaser.arrive();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
