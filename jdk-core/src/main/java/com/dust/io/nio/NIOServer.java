package com.dust.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author DUST
 * @description NIO服务器
 * @date 2022/1/7
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();

        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.configureBlocking(false);
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        ServerSocket serverSocket = ssChannel.socket();
        //绑定ip，端口
        serverSocket.bind(new InetSocketAddress("127.0.0.1",8888));

        //死循环监听
        while(true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();

                if(selectionKey.isAcceptable()){
                    //获取对应通道
                    ServerSocketChannel channel = (ServerSocketChannel)selectionKey.channel();

                    //服务器为每一个连接创建一个SocketChannel
                    SocketChannel tcpChannel = channel.accept();
                    tcpChannel.configureBlocking(false);
                    // 这个新连接主要用于从客户端读取数据
                    tcpChannel.register(selector, SelectionKey.OP_READ);

                }else if(selectionKey.isReadable()){
                    //获取对应通道
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    //输出读到的信息
                    System.out.println(readDataFromSocketChannel(channel));
                    //关闭通道
                    channel.close();

                }

                //处理完移除事件
                iterator.remove();
            }

        }

    }

    private static String readDataFromSocketChannel(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder data = new StringBuilder();

        while (true) {

            buffer.clear();
            int n = channel.read(buffer);
            if (n == -1) {
                break;
            }
            buffer.flip();
            int limit = buffer.limit();
            char[] dst = new char[limit];
            for (int i = 0; i < limit; i++) {
                dst[i] = (char) buffer.get(i);
            }
            data.append(dst);
            buffer.clear();
        }
        return data.toString();
    }
}
