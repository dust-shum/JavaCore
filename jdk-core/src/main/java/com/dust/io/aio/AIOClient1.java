package com.dust.io.aio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author DUST
 * @description NIO客户端
 * @date 2022/1/7
 */
public class AIOClient1 {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 83);
        OutputStream out = socket.getOutputStream();
        String s = "hello world";
        out.write(s.getBytes());
        out.close();
    }
}
