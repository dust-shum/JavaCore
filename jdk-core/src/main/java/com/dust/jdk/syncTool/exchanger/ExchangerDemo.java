package com.dust.jdk.syncTool.exchanger;

import java.util.concurrent.Exchanger;

/**
 * @author DUST
 * @description Exchanger类用于线程间交换数据
 * @date 2021/12/6
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        // 建一个多线程共用的exchange对象
        // 把exchange对象传给3个线程对象。每个线程在自己的run方法中调用exchange，把自己的数据作为参数
        // 传递进去，返回值是另外一个线程调用exchange传进去的参数
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(()->{
            try {
                for(;;){
                    String exchangeData = exchanger.exchange("数据1");
                    System.out.println(Thread.currentThread().getName()+"得到====》"+exchangeData);
                    //睡一下
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程1").start();

        new Thread(()->{
            try {
                for(;;){
                    String exchangeData = exchanger.exchange("数据2");
                    System.out.println(Thread.currentThread().getName()+"得到====》"+exchangeData);
                    //睡一下
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程2").start();

        new Thread(()->{
            try {
                for(;;){
                    String exchangeData = exchanger.exchange("数据3");
                    System.out.println(Thread.currentThread().getName()+"得到====》"+exchangeData);
                    //睡一下
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程3").start();


    }

}
