package com.dust.zookeepercore.zkClientDemo;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @author DUST
 * @description 获取子节点
 * @date 2022/2/4
 */
public class Get_Children_Sample {

    public static void main(String[] args) throws Exception {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        //List<String> children = zkClient.getChildren("/dust-zkClient");
        //System.out.println(children);
        //注册监听事件
        zkClient.subscribeChildChanges("/dust-zkClient", new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String>
                    currentChilds) throws Exception {
                System.out.println(parentPath + " 's child changed, currentChilds:" + currentChilds);
            }
        });
        zkClient.createPersistent("/dust-zkClient");
        Thread.sleep(1000);
        zkClient.createPersistent("/dust-zkClient/c1");
        Thread.sleep(1000);
        zkClient.delete("/dust-zkClient/c1");
        Thread.sleep(1000);
        zkClient.delete("/dust-zkClient");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
