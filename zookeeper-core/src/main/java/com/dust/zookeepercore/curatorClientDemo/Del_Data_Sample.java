package com.dust.zookeepercore.curatorClientDemo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author DUST
 * @description 删除节点
 * @date 2022/2/8
 */
public class Del_Data_Sample {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181") //server地址
                .sessionTimeoutMs(5000) // 会话超时时间
                .connectionTimeoutMs(3000) // 连接超时时间
                .retryPolicy(new ExponentialBackoffRetry(1000,5)) // 重试策略
                .build(); //
        client.start();
        System.out.println("Zookeeper session established. ");
        //删除节点
        String path = "/dust-curator";
        client.delete().deletingChildrenIfNeeded().withVersion(-1).forPath(path);
        System.out.println("success create znode"+path);
    }
}
