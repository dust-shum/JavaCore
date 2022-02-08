package com.dust.zookeepercore.curatorClientDemo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * @author DUST
 * @description 更新节点数据
 * @date 2022/2/8
 */
public class Set_Node_Sample {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181") //server地址
                .sessionTimeoutMs(5000) // 会话超时时间
                .connectionTimeoutMs(3000) // 连接超时时间
                .retryPolicy(new ExponentialBackoffRetry(1000,5)) // 重试策略
                .build(); //
        client.start();
        System.out.println("Zookeeper session established. ");
        String path = "/dust-curator/c1";
        //获取节点数据
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath(path);
        System.out.println(new String(bytes));
        //更新节点数据
        int version = client.setData().withVersion(stat.getVersion()).forPath(path).getVersion();
        System.out.println("Success set node for : " + path + ", new version: "+version);
        client.setData().withVersion(stat.getVersion()).forPath(path).getVersion();
    }
}
