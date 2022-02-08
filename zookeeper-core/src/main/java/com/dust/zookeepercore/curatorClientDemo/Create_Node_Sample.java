package com.dust.zookeepercore.curatorClientDemo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author DUST
 * @description 创建节点
 * @date 2022/2/8
 */
public class Create_Node_Sample {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181") //server地址
                .sessionTimeoutMs(5000) // 会话超时时间
                .connectionTimeoutMs(3000) // 连接超时时间
                .retryPolicy(new ExponentialBackoffRetry(1000,5)) // 重试策略
                .build(); //
        client.start();
        System.out.println("Zookeeper session established. ");
        //添加节点
        String path = "/dust-curator/c1";
        client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT).forPath(path,"init".getBytes());
        Thread.sleep(1000);
        System.out.println("success create znode"+path);
    }
}
