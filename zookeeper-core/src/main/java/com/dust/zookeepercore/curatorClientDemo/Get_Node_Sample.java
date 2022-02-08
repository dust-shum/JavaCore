package com.dust.zookeepercore.curatorClientDemo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author DUST
 * @description 获取数据
 * @date 2022/2/8
 */
public class Get_Node_Sample {

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
        System.out.println("success create znode"+path);
        //获取节点数据
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath(path);
        System.out.println(new String(bytes));
        System.out.println(stat.getAversion());
    }
}
