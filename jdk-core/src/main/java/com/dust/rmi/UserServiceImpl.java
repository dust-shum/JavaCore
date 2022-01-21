package com.dust.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DUST
 * @description 用户接口实现类
 * @date 2022/1/21
 */
public class UserServiceImpl extends UnicastRemoteObject implements IUserService{

    Map<Object, User> userMap = new HashMap();

    protected UserServiceImpl() throws RemoteException {
        super();
        User user1 = new User();
        user1.setId(1);
        user1.setName("张三");
        User user2 = new User();
        user2.setId(2);
        user2.setName("李四");

        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
    }

    @Override
    public User getByUserId(int id) throws RemoteException {
        return userMap.get(id);
    }
}
