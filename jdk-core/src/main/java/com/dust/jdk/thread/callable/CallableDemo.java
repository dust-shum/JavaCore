package com.dust.jdk.thread.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author DUST
 * @description Callable使用方式demo
 * @date 2021/12/22
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //设置Callable对象，泛型表示Callable的返回类型
        FutureTask<String> task = new FutureTask<>(new MyCallable());
        //启动处理线程
        new Thread(task).start();

        //同步阻塞等待结果
        String result = task.get();
        System.out.println(result);

    }


    public static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            return "hello world";
        }
    }
}
