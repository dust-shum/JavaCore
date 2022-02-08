package com.dust.zookeepercore.zkClientDemo;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author DUST
 * @description 删除节点
 * @date 2022/2/4
 */
public class Del_Data_Sample {

    public static void main(String[] args) throws Exception {
        String path = "/dust-zkClient";
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        zkClient.deleteRecursive(path);
        System.out.println("success delete znode.");
    }
}
