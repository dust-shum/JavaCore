package com.dust.demo.easyRpc.client;

import com.dust.demo.easyRpc.pojo.User;
import com.dust.demo.easyRpc.service.IUserService;

/**
 * @author DUST
 * @description 客户端启动类
 * @date 2022/1/21
 */
public class ClientBootStrap {

    public static void main(String[] args) {
        IUserService userService = (IUserService) RpcClientProxy.createProxy(IUserService.class);
        User user = userService.getByUserId(1);
        System.out.println(user);
    }
}
