package com.dust.zookeepercore.zkClientDemo;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author DUST
 * @description 创建节点
 * @date 2022/2/4
 */
public class Create_Node_Sample {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("ZooKeeper session established.");
        //createParents的值设置为true，可以递归创建节点
        zkClient.createPersistent("/dust-zkClient/dust-c1",true);
        System.out.println("success create znode.");
    }
}
