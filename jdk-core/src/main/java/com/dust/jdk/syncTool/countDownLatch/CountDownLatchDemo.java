package com.dust.jdk.syncTool.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author DUST
 * @description CountDownLatch使用演示说明
 * @date 2021/11/3
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        //模拟五个线程执行结束之后，才能执行主线程的内容
        new MyThread("t1",countDownLatch).start();
        new MyThread("t2",countDownLatch).start();
        new MyThread("t3",countDownLatch).start();
        new MyThread("t4",countDownLatch).start();
        new MyThread("t5",countDownLatch).start();

        //等五个线程执行完之后，才能执行后续的逻辑，不然会一直阻塞等待
        countDownLatch.await();

        //主线程执行的内容
        System.out.println("主线程的内容");
    }

    private static class MyThread extends Thread{

        private final CountDownLatch countDownLatch;

        private MyThread(String name,CountDownLatch countDownLatch) {
            super(name);
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                //当前线程睡眠1s，模拟执行某个逻辑1s
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+"的内容执行完毕");
                //当前线程执行完之后，报告执行完了
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
