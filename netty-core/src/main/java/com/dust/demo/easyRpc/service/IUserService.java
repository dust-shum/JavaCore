package com.dust.demo.easyRpc.service;

import com.dust.demo.easyRpc.pojo.User;

import java.rmi.RemoteException;

/**
 * @author DUST
 * @description 用户接口
 * @date 2022/1/21
 */
public interface IUserService{

    User getByUserId(int id);
}
