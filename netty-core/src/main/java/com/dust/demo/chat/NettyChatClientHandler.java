package com.dust.demo.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author DUST
 * @description 聊天室客户端处理器
 * @date 2022/1/14
 */
public class NettyChatClientHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //输出消息
        System.out.println(s);
    }
}
