package com.dust.io.bio;

import java.util.concurrent.CountDownLatch;

/**
 * @author DUST
 * @description 模拟多个客户端同时向服务器发送请求，等待服务器的响应消息
 * @date 2022/1/4
 */
public class SocketClientDaemon {

    public static void main(String[] args) throws InterruptedException {
        //20个客户端线程
        int clientNum = 20;
        CountDownLatch countDownLatch = new CountDownLatch(clientNum);

        //分别开始启动这20个客户端
        for (int i = 0; i < clientNum; i++,countDownLatch.countDown()) {
            SocketClientRequestThread client = new SocketClientRequestThread(countDownLatch, i+1);
            new Thread(client).start();
        }

        //这个wait不涉及到具体的实验逻辑，只是为了保证守护线程在启动所有线程后，进入等待状态
        synchronized (SocketClientDaemon.class) {
            SocketClientDaemon.class.wait();
        }

    }
}
