package com.dust.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author DUST
 * @description RMI服务端
 * @date 2022/1/21
 */
public class RMIServer {

    static Object lock = new Object();

    /**
     * 需求分析:
     *      1. 服务端提供根据ID查询用户的方法
     *      2. 客户端调用服务端方法, 并返回用户对象
     *      3. 要求使用RMI进行远程通信
    */
    public static void main(String[] args) throws RemoteException, InterruptedException {
        //1.注册Registry实例. 绑定端口
        Registry registry = LocateRegistry.createRegistry(9998);
        // 2.创建远程对象
        IUserService userService = new UserServiceImpl();
        // 3.将远程对象注册到RMI服务器上即(服务端注册表上)
        registry.rebind("userService", userService);
        System.out.println("---RMI服务端启动成功----");

        synchronized (lock){
            lock.wait();
        }
    }
}
