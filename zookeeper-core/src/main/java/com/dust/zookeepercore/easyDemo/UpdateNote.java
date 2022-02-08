package com.dust.zookeepercore.easyDemo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @author DUST
 * @description 修改节点数据
 * @date 2022/2/4
 */
public class UpdateNote implements Watcher{

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("localhost:2181", 10000, new
                UpdateNote());
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //当连接创建了，服务端发送给客户端SyncConnected事件
        try {
            updateNodeSync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateNodeSync() throws Exception {
        /*
        path:路径
        data:要修改的内容 byte[]
        version:为-1，表示对最新版本的数据进⾏修改
        zooKeeper.setData(path, data,version);
        */
        byte[] data = zooKeeper.getData("/dust_persistent", false, null);
        System.out.println("修改前的值:"+new String(data));
        //修改 stat:状态信息对象 -1:最新版本
        Stat stat = zooKeeper.setData("/dust_persistent", "客户端修改内容".getBytes(), -1);
        byte[] data2 = zooKeeper.getData("/dust_persistent", false, null);
        System.out.println("修改后的值:"+new String(data2));
    }

}
