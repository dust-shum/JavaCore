package com.dust.zookeepercore.zkClientDemo;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author DUST
 * @description 获取数据（节点是否存在、更新、删除）
 * @date 2022/2/4
 */
public class Get_Data_Sample {

    public static void main(String[] args) throws InterruptedException {
        String path = "/dust-zkClient-Ep";
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        //判断节点是否存在
        boolean exists = zkClient.exists(path);
        if (!exists){
            zkClient.createEphemeral(path, "123");
        }

        //注册监听
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            public void handleDataChange(String path, Object data) throws
                    Exception {
                System.out.println(path+"该节点内容被更新，更新后的内容"+data);
            }
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s+" 该节点被删除");
            }
        });
        //获取节点内容
        Object o = zkClient.readData(path);
        System.out.println(o);
        //更新
        zkClient.writeData(path,"4567");
        Thread.sleep(1000);
        //删除
        zkClient.delete(path);
        Thread.sleep(1000);
    }
}
