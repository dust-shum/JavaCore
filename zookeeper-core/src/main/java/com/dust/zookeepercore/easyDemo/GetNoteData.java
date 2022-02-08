package com.dust.zookeepercore.easyDemo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author DUST
 * @description 获取节点数据
 * @date 2022/2/4
 */
public class GetNoteData implements Watcher {

    //countDownLatch这个类使⼀个线程等待,主要不让main⽅法结束
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("localhost:2181", 10000, new
                GetNoteData());
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //⼦节点列表发⽣变化时，服务器会发出NodeChildrenChanged通知，但不会把变化情况告诉给客户端
        // 需要客户端⾃⾏获取，且通知是⼀次性的，需反复注册监听
        if(watchedEvent.getType() ==Event.EventType.NodeChildrenChanged){
            //再次获取节点数据
            try {
                List<String> children =
                        zooKeeper.getChildren(watchedEvent.getPath(), true);
                System.out.println(children);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //当连接创建了，服务端发送给客户端SyncConnected事件
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
            try {
                //调⽤获取单个节点数据⽅法
                getNoteDate();
                getChildrens();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void getChildrens() throws InterruptedException, KeeperException {
        /*
            path:路径
            watch:是否要启动监听，当⼦节点列表发⽣变化，会触发监听
            zooKeeper.getChildren(path, watch);
        */
        List<String> children = zooKeeper.getChildren("/dust_persistent", true);
        System.out.println(children);
    }

    private static void getNoteDate() throws Exception {
        /**
         * path : 获取数据的路径
         * watch : 是否开启监听
         * stat : 节点状态信息
         * null: 表示获取最新版本的数据
         * zk.getData(path, watch, stat);
         */
        byte[] data = zooKeeper.getData("/dust_persistent/dust_children", true, null);
        System.out.println(new String(data,"utf-8"));
    }
}
