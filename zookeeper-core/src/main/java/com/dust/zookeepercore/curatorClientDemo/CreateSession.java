package com.dust.zookeepercore.curatorClientDemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author DUST
 * @description 创建会话
 * @date 2022/2/8
 */
public class CreateSession {

    public static void main(String[] args) {
        //new CreateSession().createSessionMethodOne();
        new CreateSession().createSessionMethodTwo();
        System.out.println("Zookeeper session1 established. ");

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client1 = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181") //server地址
                .sessionTimeoutMs(5000) // 会话超时时间
                .connectionTimeoutMs(3000) // 连接超时时间
                .retryPolicy(retryPolicy) // 重试策略
                .namespace("base") // ᇿ⽴命名空间/base
                .build(); //
        client1.start();
        System.out.println("Zookeeper session2 established. ");
    }

    public void createSessionMethodOne(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework client =
                CuratorFrameworkFactory.newClient("127.0.0.1:2181",retryPolicy);
        client.start();
    }

    public void createSessionMethodTwo(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",
                5000,1000,retryPolicy);
        client.start();
    }
}
