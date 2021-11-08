package com.dust.jdk.threadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author DUST
 * @description 关闭线程池的demo
 * @date 2021/11/4
 */
public class closeThreadPoolDemo {

    public static void main(String[] args) {
        //模拟一个真实的线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //线程池正在工作中
        System.out.println("线程池working");
        //现在优雅的关闭线程池
        executorService.shutdown();
        //正在关闭的标志
        boolean flag = true;
        //不断检测线程池是否已经结束工作了
        while(flag){
            try {
                //awaitTermination(...)方法的内部实现很简单，如下所示。不断循环判断线程池是否到达了最终状态TERMINATED
                // 如果是，就返回；如果不是，则通过termination条件变量阻塞一段时间，之后继续判断。
                flag = !executorService.awaitTermination(1, TimeUnit.SECONDS);
                System.out.println("是否已经结束工作:"+!flag);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
