package com.dust.demo.chat;

import com.dust.demo.simple.MessageCoder;
import com.dust.demo.simple.MessageEncoder;
import com.dust.demo.simple.NettyServerHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author DUST
 * @description 聊天室的Netty服务端：可以监测用户上线，离线，并实现消息转发功能
 * @date 2022/1/14
 */
public class NettyChatServer {

    //端口号
    private int port;

    public NettyChatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        //启动服务端
        new NettyChatServer(9998).run();
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = null;
        EventLoopGroup workerGroup = null;
        try {
            //1.创建bossGroup线程组: 处理网络事件--连接事件 线程数默认为: 2 * 处理器线程数
            bossGroup = new NioEventLoopGroup(1);
            //2.创建workerGroup线程组: 处理网络事件--读写事件 2 * 处理器线程数
            workerGroup = new NioEventLoopGroup();
            //3.创建服务端启动助手
            ServerBootstrap bootstrap = new ServerBootstrap();
            //4.设置线程组
            bootstrap.group(bossGroup, workerGroup)
                    //5.设置服务端通道实现;
                    .channel(NioServerSocketChannel.class)
                    //6.参数设置-设置线程队列中等待 连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //7.参数设置-设置活跃状态,child是设置workerGroup
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    //8.创建一个通道初始化对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //9.向pipeline中添加自定义业务处理handler
                            ch.pipeline().addLast(new MessageDecoder());
                            ch.pipeline().addLast(new MessageEncoder());
                            ch.pipeline().addLast(new NettyChatServerHandler());
                        }
                    });
            //10.启动服务端并绑定端口,同时将异步改为同步(通过sync改为同步等待初始化的完成，否则立即操作对象(未初始完全)可能会报错)
            ChannelFuture future = bootstrap.bind(port).sync();

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("端口绑定成功！");
                    }else{
                        System.out.println("端口绑定失败！");
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
            System.out.println("聊天室服务端启动成功~");
            //11.关闭通道(并不是真正意义上关闭,而是阻塞监听通道关闭的状态)
            future.channel().closeFuture().sync();

        } finally {
            //12.关闭连接池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
