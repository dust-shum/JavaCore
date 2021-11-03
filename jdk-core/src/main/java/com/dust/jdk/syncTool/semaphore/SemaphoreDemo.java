package com.dust.jdk.syncTool.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @author DUST
 * @description semaphore类用法演示
 * @date 2021/11/3
*/
public class SemaphoreDemo {

    public static void main(String[] args) {
        //大学生抢自习室座位，座位只有五个
        Semaphore semaphore = new Semaphore(5);

        //十个学生抢座位
        for (int i = 0; i < 10; i++) {
            new StudentThread("学生"+(i+1),semaphore).start();
        }

    }

    private static class StudentThread extends Thread{

        private final Semaphore semaphore;

        public StudentThread(String name,Semaphore semaphore) {
            super(name);
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                //单个学生开始抢座位
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+" - 抢座位成功，开始自习");
                //自习1秒
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+" - 自习结束，腾出座位");
                //释放座位
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
