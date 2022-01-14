package com.dust.demo.chat;

import com.dust.demo.simple.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @author DUST
 * @description 聊天室的Netty客户端：可以发送消息给其它所有用户，同时可以接受其它用户发送的消息
 * @date 2022/1/14
 */
public class NettyChatClient {

    //服务器端口号
    private int port;
    //服务器ip
    private String ip;

    public NettyChatClient(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public NettyChatClient(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        //启动客户端
        new NettyChatClient(9998,"127.0.0.1").run();
    }

    public void run() throws InterruptedException {
        EventLoopGroup group = null;
        try {
            //1.创建线程组: 处理网络事件--读写事件 线程数默认为: 2 * 处理器线程数
            group = new NioEventLoopGroup();
            //2.创建客户端启动助手
            Bootstrap bootstrap = new Bootstrap();
            //3.设置线程组
            bootstrap.group(group)
                    //5.设置客户端通道实现;
                    .channel(NioSocketChannel.class)
                    //6.创建一个通道初始化对象
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //7.向pipeline中添加自定义业务处理handler
                            ch.pipeline().addLast(new MessageDecoder());
                            ch.pipeline().addLast(new MessageEncoder());
                            ch.pipeline().addLast(new NettyChatClientHandler());
                        }
                    });
            //8.启动客户端,等待连接服务端,同时将异步改为同步
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            System.out.println("聊天室客户端启动成功~");

            Channel channel = future.channel();
            System.out.println("-------" + channel.localAddress().toString().substring(1) + "--------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                //向服务端发送消息
                channel.writeAndFlush(msg);
            }

            //9.关闭通道(并不是真正意义上关闭,而是阻塞监听通道关闭的状态)
            future.channel().closeFuture().sync();

        } finally {
            //10.关闭连接池
            group.shutdownGracefully();
        }
    }
}
