package com.dust.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author DUST
 * @description RMI客户端
 * @date 2022/1/21
 */
public class RMIClient {

    static Object lock = new Object();

    /**
     * 需求分析:
     *      1. 服务端提供根据ID查询用户的方法
     *      2. 客户端调用服务端方法, 并返回用户对象
     *      3. 要求使用RMI进行远程通信
    */
    public static void main(String[] args) throws Exception {
        //1.获取Registry实例
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9998);
        //2.通过Registry实例查找远程对象
        IUserService userService = (IUserService) registry.lookup("userService");
        User user = userService.getByUserId(2);
        System.out.println(user.getId() + "----" + user.getName());

        synchronized (lock){
            lock.wait();
        }
    }
}
