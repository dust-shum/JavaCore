package com.dust.zookeepercore.zkClientDemo;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author DUST
 * @description 创建会话
 * @date 2022/2/4
 */
public class CreateSession {

    /*
    创建⼀个zkClient实例来进⾏连接
    注意：zkClient通过对zookeeperAPI内部包装，将这个异步的会话创建过程同步化了
    */
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("ZooKeeper session established.");
    }
}
