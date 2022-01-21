package com.dust.demo.easyRpc.client;

import com.dust.demo.easyRpc.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author DUST
 * @description Rpc框架客户端
 * @date 2022/1/21
 */
public class RpcClient {

    /**
     * 客户端
     * 1.连接Netty服务端
     * 2.提供给调用者主动关闭资源的方法
     * 3.提供消息发送的方法
     */
    private EventLoopGroup group;
    private Channel channel;
    private String ip;
    private int port;


    private RpcClientHandler rpcClientHandler = new RpcClientHandler();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public RpcClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        initClient();
    }

    /**
     * @author DUST
     * @description 初始化方法-连接Netty服务端
     * @date 2022/1/21
     * @param
     * @return void
    */
    public void initClient() {
        try {
            //1.创建线程组
            group = new NioEventLoopGroup();
            // 2.创建启动助手
            Bootstrap bootstrap = new Bootstrap();
            // 3.设置参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            //String类型编解码器
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            //添加客户端处理类
                            pipeline.addLast(rpcClientHandler);
                        }
                    });
            //4.连接Netty服务端
            channel = bootstrap.connect(ip, port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            if (channel != null) {
                channel.close();
            }
            if (group != null) {
                group.shutdownGracefully();
            }
        }
    }

    /**
     * @author DUST
     * @description 提供给调用者主动关闭资源的方法
     * @date 2022/1/21
     * @param
     * @return void
    */
    public void close(){
        if (channel != null) {
            channel.close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
    }

    /**
     * @author DUST
     * @description 提供消息发送的方法
     * @date 2022/1/21
     * @param msg
     * @return java.lang.Object
    */
    public Object send(String msg) throws Exception {
        rpcClientHandler.setRequestMsg(msg);
        Future submit = executorService.submit(rpcClientHandler);
        return submit.get();
    }
}
