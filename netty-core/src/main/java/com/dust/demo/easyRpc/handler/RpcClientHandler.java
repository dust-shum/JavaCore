package com.dust.demo.easyRpc.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * @author DUST
 * @description Rpc客户端处理器 1.发送消息 2.接收消息
 * @date 2022/1/21
 */
public class RpcClientHandler  extends SimpleChannelInboundHandler<String> implements Callable {

    ChannelHandlerContext context;

    //发送的消息
    String requestMsg;
    //服务端的消息
    String responseMsg;

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    /**
     * @author DUST
     * @description 通道连接就绪事件
     * @date 2022/1/21
     * @param ctx
     * @return void
    */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }



    /**
     * @author DUST
     * @description 通道读取就绪事件
     * @date 2022/1/21
     * @param channelHandlerContext
     * @param msg
     * @return void
    */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        responseMsg = msg;
        synchronized (this){
            this.notify();
        }
    }

    /**
     * @author DUST
     * @description 发送消息到服务端
     * @date 2022/1/21
     * @param
     * @return java.lang.Object
    */
    @Override
    public Object call() throws Exception {
        //消息发送
        context.writeAndFlush(requestMsg);
        synchronized (this){
            this.wait();
        }

        return responseMsg;
    }
}
