package com.dust.demo.easyRpc.common;

import lombok.Data;

/**
 * @author DUST
 * @description Rpc响应数据的封装类
 * @date 2022/1/21
 */
@Data
public class RpcResponse {

    /**
     * 响应ID
     */
    private String requestId;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 返回的结果
     */
    private Object result;
}
