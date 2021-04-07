package com.pgz.net.tcp.nio.demo;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件通道
 *
 * @author liquan_pgz@qq.com
 * @date 2021-03-14
 */
public class FileChannelTest {

    @Test
    public void testChannel() throws Exception {
        FileInputStream fis = new FileInputStream("E:\\myCode\\idea\\gitlab\\algorithm\\src\\main\\resources\\file\\pro.txt");
        FileOutputStream fos = new FileOutputStream("E:\\myCode\\idea\\gitlab\\algorithm\\src\\main\\resources\\file\\pro_copy.txt");
        FileChannel fisChannel = fis.getChannel();
        FileChannel channel = fos.getChannel();
        channel.transferFrom(fisChannel, 0, fisChannel.size());

        fisChannel.close();
        channel.close();
        fis.close();
        fos.close();

    }

    @Test
    public void testBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.put((byte) 1);
        byteBuffer.putChar('F');
        byteBuffer.putLong(111);
        byteBuffer.flip();
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getLong());
        byte b = byteBuffer.get();
        System.out.println(b);
    }

    @Test
    public void testInteger() {
        int[] intArr = new int[1000000];
        String[] strArr1 = new String[1000000];//为了公平分别定义三个数组

        String[] strArr2 = new String[1000000];
        String[] strArr3 = new String[1000000];
        //赋值
        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            intArr[i] = i + 1;
        }
        Long t2 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            strArr1[i] = String.valueOf(intArr[i]);
        }
        Long t3 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            strArr2[i] = Integer.toString(intArr[i]);
        }
        Long t4 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            strArr3[i] = intArr[i] + "";
        }
        Long t5 = System.currentTimeMillis();
        System.out.println("t1 = " + t1);
        System.out.println("t2 = " + t2);
        System.out.println("t3 = " + t3);
        System.out.println("t4 = " + t4);
        System.out.println("t5 = " + t5);
        System.out.println("赋值：" + (t2 - t1));
        System.out.println("String.valueOf(i)：" + (t3 - t2));
        System.out.println("Integer.toString(i)：" + (t4 - t3));
        System.out.println("i+\"\"：" + (t5 - t4));
    }

    @Test
    public void testInt() {
        Long l = Integer.MAX_VALUE + 2L;
        Integer integer = -2147483647;
        Map<Object, String> map = new HashMap<>();
        map.put(l, "l");
        map.put(integer, "dd");
        System.out.println(map);
        System.out.println(l.equals(integer));

        List a = new ArrayList<>();

        List<String> strings = Arrays.asList("22", "222", "232","232","232","4444", "44444");
        List<String> strings1 = Arrays.asList("22", "222");
        long count = strings.stream().filter(item -> item.length() < 4).filter(item -> strings1.contains(item)).count();
        System.out.println(count);
    }

}
