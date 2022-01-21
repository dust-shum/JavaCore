package com.dust.demo.easyRpc.server;

import com.dust.demo.easyRpc.handler.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author DUST
 * @description Rpc框架服务端
 * @date 2022/1/21
 */
@Component
public class RpcServer implements DisposableBean {
    /**
     * 需求：
     *     用 Netty 实现一个简单的 RPC 框架，消费者和提供者约定接口和协议，消费者远程调用提供者的服务
     *      1. 创建一个接口，定义抽象方法。用于消费者和提供者之间的约定
     *      2. 创建一个提供者，该类需要监听消费者的请求，并按照约定返回数据
     *      3. 创建一个消费者，该类需要透明的调用自己不存在的方法，内部需要使用 Netty 进行数据通信
     *      4. 提供者与消费者数据传输使用json字符串数据格式
     *      5. 提供者使用netty集成spring boot 环境实现
    */

    @Autowired
    public RpcServerHandler rpcServerHandler;

    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;

    public void startServer(String ip,int port){
        try {
            //1. 创建线程组
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            //2. 创建服务端启动助手
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //3. 设置参数
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            //添加String的编解码器
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            //业务处理类
                            pipeline.addLast(rpcServerHandler);
                        }
                    });
            //4.绑定端口
            ChannelFuture sync = serverBootstrap.bind(ip, port).sync();
            System.out.println("==========服务端启动成功==========");
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(bossGroup != null){
                bossGroup.shutdownGracefully();
            }
            if(workerGroup != null){
                workerGroup.shutdownGracefully();
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        if(bossGroup != null){
            bossGroup.shutdownGracefully();
        }
        if(workerGroup != null){
            workerGroup.shutdownGracefully();
        }
    }
}
