package com.dust.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author DUST
 * @description 用户接口
 * @date 2022/1/21
 */
public interface IUserService extends Remote {

    User getByUserId(int id) throws RemoteException;
}
