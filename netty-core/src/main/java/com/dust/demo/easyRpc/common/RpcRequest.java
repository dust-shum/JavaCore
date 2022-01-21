package com.dust.demo.easyRpc.common;

import lombok.Data;

/**
 * @author DUST
 * @description Rpc请求数据的封装类
 * @date 2022/1/21
 */
@Data
public class RpcRequest {
    /**
     * 请求对象的ID
     */
    private String requestId;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 入参
     */
    private Object[] parameters;
}
