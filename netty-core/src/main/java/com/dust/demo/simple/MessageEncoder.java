package com.dust.demo.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author DUST
 * @description netty编码处理器demo
 * @date 2022/1/14
 */
public class MessageEncoder extends MessageToMessageEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        System.out.println("消息进行消息编码");
        out.add(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }

}
