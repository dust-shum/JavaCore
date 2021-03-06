package com.dust.demo.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/**
 * @author DUST
 * @description 自定义netty服务器处理器demo
 * @date 2022/1/13
 */
public class NettyServerHandle implements ChannelInboundHandler {
    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    /**
     * @author DUST 通道就绪事件
     * @description
     * @date 2022/1/13
     * @param channelHandlerContext 通道上下文对象
     * @return void
    */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    /**
     * @author DUST
     * @description 通道读取事件
     * @date 2022/1/13
     * @param channelHandlerContext 通道上下文对象
     * @param o
     * @return void
    */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("客户端发来消息:" + o);
    }

    /**
     * @author DUST
     * @description 读取完毕事件
     * @date 2022/1/13
     * @param channelHandlerContext 通道上下文对象
     * @return void
    */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        //将数据写到ChannelPipeline中当前ChannelHandler的下一个ChannelHandler开始处理（出站）
        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush("你好，我是netty服务器");
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("数据发送成功！");
                }else{
                    System.out.println("数据发送失败！");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    /**
     * @author DUST
     * @description 异常发生事件
     * @date 2022/1/13
     * @param channelHandlerContext 通道上下文对象
     * @param throwable
     * @return void
    */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {

    }
}
