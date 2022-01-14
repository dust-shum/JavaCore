package com.dust.demo.chat;

import io.netty.channel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DUST
 * @description 聊天室服务端处理器
 * @date 2022/1/14
 */
public class NettyChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static List<Channel> channelList = new ArrayList<>();

    /**
     * @author DUST
     * @description 通道就绪事件
     * @date 2022/1/14
     * @param ctx 通道上下文
     * @return void
    */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //当有新的客户端连接进来的时候，将通道放入集合
        channelList.add(channel);
        System.out.println("[Server]:" + channel.remoteAddress().toString().substring(1) + "在线.");
    }

    /**
     * @author DUST
     * @description 通道未就绪事件-channel下线
     * @date 2022/1/14
     * @param ctx 通道上下文
     * @return void
    */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //当有客户端断开连接的时候,就移除对应的通道
        channelList.remove(channel);
        System.out.println("[Server]:" + channel.remoteAddress().toString().substring(1) + "下线.");
    }

    /**
     * @author DUST
     * @description 通道异常事件
     * @date 2022/1/14
     * @param ctx
     * @param cause
     * @return void
    */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Channel channel = ctx.channel();
        //移除集合
        channelList.remove(channel);
        System.out.println("[Server]:" + channel.remoteAddress().toString().substring(1) + "异常.");
    }

    /**
     * @author DUST
     * @description 通道读取事件
     * @date 2022/1/14
     * @param ctx 通道上下文
     * @param msg 消息
     * @return void
    */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //当前发送消息的通道, 当前发送的客户端连接
        Channel channel = ctx.channel();
        for (Channel var : channelList) {
            //排除自身通道
            if (channel != var) {
                var.writeAndFlush("[" + channel.remoteAddress().toString().substring(1) + "]说:" + msg);
            }
        }
    }
}
