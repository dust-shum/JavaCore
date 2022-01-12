package com.dust.io.nio.zeroCopy;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


/**
 * @author DUST
 * @description FileChannel 类 demo
 * @date 2022/1/12
 */
public class FileChannelDemo {

    private static final String CONTENT = "Zero copy implemented by FileChannel";
    private static final String SOURCE_FILE = "/Users/dust/IdeaProjects/JavaCore/jdk-core/target/classes/source.txt";
    private static final String TARGET_FILE = "/Users/dust/IdeaProjects/JavaCore/jdk-core/target/classes/target.txt";
    private static final String CHARSET = "UTF-8";

    public static void main(String[] args) throws Exception {
        //new FileChannelDemo().setup();
        //new FileChannelDemo().transferTo();
        new FileChannelDemo().transferFrom();

    }

    /**
     * @author DUST
     * @description 通过 transferFrom() 将 fromChannel 中的数据拷贝到 toChannel
     * @date 2022/1/12
     * @param
     * @return void
     */
    public void transferFrom() throws Exception {
        try (FileChannel fromChannel = new RandomAccessFile(
                new File(SOURCE_FILE), "rw").getChannel();
             FileChannel toChannel = new RandomAccessFile(
                     new File(TARGET_FILE), "rw").getChannel()) {
            long position = 0L;
            long offset = fromChannel.size();
            toChannel.transferFrom(fromChannel, position, offset);
        }
    }


    /**
     * @author DUST
     * @description 通过 transferTo() 将 fromChannel 中的数据拷贝到 toChannel
     * @date 2022/1/12
     * @param
     * @return void
    */
    public void transferTo() throws Exception {
        try (FileChannel fromChannel = new RandomAccessFile(
                new File(SOURCE_FILE), "r").getChannel();
             FileChannel toChannel = new RandomAccessFile(
                     new File(TARGET_FILE), "rw").getChannel()) {
            long position = 0L;
            long offset = fromChannel.size();
            fromChannel.transferTo(position, offset, toChannel);
        }
    }


    /**
     * @author DUST
     * @description 对源文件 source.txt 文件写入初始化数据
     * @date 2022/1/12
     * @param
     * @return void
    */
    public void setup() {
        Path source = Paths.get(SOURCE_FILE);
        //Path source = Paths.get(getClass().getResource(SOURCE_FILE).getPath());
        byte[] bytes = CONTENT.getBytes(Charset.forName(CHARSET));
        try (FileChannel fromChannel = FileChannel.open(source, StandardOpenOption.READ,
                StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            fromChannel.write(ByteBuffer.wrap(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
