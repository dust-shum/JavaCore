package com.dust.zookeepercore.easyDemo;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author DUST
 * @description 创建节点
 * @date 2022/1/31
 */
public class CreateNote implements Watcher {

    //countDownLatch这个类使⼀个线程等待,主要不让main⽅法结束
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNote());
        countDownLatch.await();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //当连接创建了，服务端发送给客户端SyncConnected事件
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
            try {
                createNodeSync();
            } catch (Exception e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
        //调⽤创建节点⽅法
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNodeSync() throws Exception {
        /**
         * path ：节点创建的路径
         * data[] ：节点创建要保存的数据，是个byte类型的
         * acl ：节点创建的权限信息(4种类型)
         * ANYONE_ID_UNSAFE : 表示任何⼈
         * AUTH_IDS ：此ID仅可⽤于设置ACL。它将被客户机验证的ID替
         换。
         * OPEN_ACL_UNSAFE ：这是⼀个完全开放的ACL(常⽤)-->
         world:anyone
         * CREATOR_ALL_ACL ：此ACL授予创建者身份验证ID的所有权限
         * createMode ：创建节点的类型(4种类型)
         * PERSISTENT：持久节点
         * PERSISTENT_SEQUENTIAL：持久顺序节点
         * EPHEMERAL：临时节点
         * EPHEMERAL_SEQUENTIAL：临时顺序节点
         String node = zookeeper.create(path,data,acl,createMode);
         */
        String node_PERSISTENT = zooKeeper.create(
                "/dust_persistent", "持久节点内容".getBytes("utf-8"),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        String node_PERSISTENT_SEQUENTIAL = zooKeeper.create(
                "/dust_persistent_sequential", "持久顺序节点内容".getBytes("utf-8"),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        String node_EPERSISTENT = zooKeeper.create(
                "/dust_ephemeral", "临时节点内容".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("创建的持久节点是:"+node_PERSISTENT);
        System.out.println("创建的持久顺序节点是:"+node_PERSISTENT_SEQUENTIAL);
        System.out.println("创建的临时节点是:"+node_EPERSISTENT);
    }
}
