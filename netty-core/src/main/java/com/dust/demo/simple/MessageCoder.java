package com.dust.demo.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author DUST
 * @description netty自定义编解码处理器demo
 * @date 2022/1/14
 */
public class MessageCoder extends MessageToMessageCodec {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
        System.out.println("正在进行消息编码");
        String str = (String) o;
        list.add(Unpooled.copiedBuffer(str, CharsetUtil.UTF_8));
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
        System.out.println("正在进行消息解码");
        ByteBuf byteBuf = (ByteBuf) o;
        list.add(byteBuf.toString(CharsetUtil.UTF_8));
    }
}
