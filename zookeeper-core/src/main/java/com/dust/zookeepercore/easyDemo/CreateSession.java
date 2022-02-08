package com.dust.zookeepercore.easyDemo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author DUST
 * @description 建立会话
 * @date 2022/1/31
 */
public class CreateSession implements Watcher {

    //countDownLatch这个类使⼀个线程等待,主要不让main⽅法结束
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        /*
        客户端可以通过创建⼀个zk实例来连接zk服务器
        new Zookeeper(connectString,sesssionTimeOut,Wather)
        connectString: 连接地址：IP：端⼝
        sesssionTimeOut：会话超时时间：单位毫秒
        Wather：监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
        */
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new
                CreateSession());
        System.out.println(zooKeeper.getState());
        countDownLatch.await();
        //表示会话真正建⽴
        System.out.println("=========Client Connected to zookeeper==========");
    }

    //当前类实现了Watcher接⼝，重写了process⽅法，该⽅法负责处理来⾃Zookeeper服务端的
    //watcher通知，在收到服务端发送过来的SyncConnected事件之后，解除主程序在CountDownLatch上
    //的等待阻塞，⾄此，会话创建完毕
    @Override
    public void process(WatchedEvent watchedEvent) {
        //当连接创建了，服务端发送给客户端SyncConnected事件
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
        }
    }
}
