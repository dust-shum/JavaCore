package com.dust.demo.easyRpc.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dust.demo.easyRpc.common.RpcRequest;
import com.dust.demo.easyRpc.common.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author DUST
 * @description Rpc客户端代理类-创建代理对象
 * @date 2022/1/21
 */
public class RpcClientProxy {

    /**
     * @author DUST
     * @description 创建代理对象
     * @date 2022/1/21
     * @param serviceClass
     * @return java.lang.Object
    */
    public static Object createProxy(Class serviceClass) {

        /***
         * 客户端代理类-创建代理对象
         * 1.封装request请求对象
         * 2.创建RpcClient对象
         * 3.发送消息
         * 4.返回结果
         */
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //1. 封装RpcRequest请求对象
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId(UUID.randomUUID().toString());
                        rpcRequest.setClassName(method.getDeclaringClass().getName());
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setParameterTypes(method.getParameterTypes());
                        rpcRequest.setParameters(args);
                        //2. 封装RpcClient客户端对象
                        RpcClient rpcClient = new RpcClient("127.0.0.1", 8899);
                        try {
                            //3. 发送消息(这里是阻塞等待到结果返回）
                            Object rpcResponseMsg = rpcClient.send(JSON.toJSONString(rpcRequest));
                            RpcResponse rpcResponse = JSON.parseObject(rpcResponseMsg.toString(), RpcResponse.class);
                            System.out.println("请求结果:"+ JSON.toJSONString(rpcResponse));
                            //如果结果不正常，抛错
                            if(rpcResponse.getError() != null){
                                System.out.println("抛错");
                                throw new RuntimeException(rpcResponse.getError());
                            }
                            //4.返回结果
                            return JSON.parseObject(rpcResponse.getResult().toString(),method.getReturnType());
                        } catch (Exception e) {
                            System.out.println("抛错");
                            throw e;
                        } finally {
                            System.out.println("关闭客户端连接");
                            rpcClient.close();
                        }
                    }
                });
    }
}
