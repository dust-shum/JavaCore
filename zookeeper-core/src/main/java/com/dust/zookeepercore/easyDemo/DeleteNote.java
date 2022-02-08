package com.dust.zookeepercore.easyDemo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @author DUST
 * @description 删除节点
 * @date 2022/2/4
 */
public class DeleteNote implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("localhost:2181", 10000, new
                DeleteNote());
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //当连接创建了，服务端发送给客户端SyncConnected事件
        try {
            deleteNodeSync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteNodeSync() throws Exception {
        /*
        zooKeeper.exists(path,watch) :判断节点是否存在
        zookeeper.delete(path,version) : 删除节点
        */
        Stat exists = zooKeeper.exists("/dust_persistent/dust_children", false);
        System.out.println(exists == null ? "该节点不存在":"该节点存在");
        zooKeeper.delete("/dust_persistent/dust_children",-1);
        Stat exists2 = zooKeeper.exists("/dust_persistent/dust_children", false);
        System.out.println(exists2 == null ? "该节点不存在":"该节点存在");
    }
}
