package com.dust.demo.easyRpc.service.impl;

import com.dust.demo.easyRpc.RpcService;
import com.dust.demo.easyRpc.pojo.User;
import com.dust.demo.easyRpc.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DUST
 * @description 用户接口实现类
 * @date 2022/1/21
 */
@Service
@RpcService
public class UserServiceImpl implements IUserService {

    Map<Object, User> userMap = new HashMap();

    @Override
    public User getByUserId(int id) {
        if(userMap.size() == 0){
            User user1 = new User();
            user1.setId(1);
            user1.setName("张三");
            User user2 = new User();
            user2.setId(2);
            user2.setName("李四");

            userMap.put(user1.getId(), user1);
            userMap.put(user2.getId(), user2);
        }

        return userMap.get(id);
    }
}
