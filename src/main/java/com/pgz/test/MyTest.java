package com.pgz.test;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-21
 */
public class MyTest {

    /**
     * Integer 内部存在缓存 -128~127
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-09-21
     **/
    @Test
    public void testInteger() {
        Integer a = 1000, b = 1000;
        System.out.println(a == b);
        Integer c = 100, d = 100;
        System.out.println(c == d);
    }

    /**
     * leftPad 左边填充数据
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-09-23
     **/
    @Test
    public void testByte() {
        byte byteValue = '\'';
        // 将byte转换为8位二进制字符串 依赖 commons-lang-x.x.jar包
        System.out.println(byteValue & 0xff);
        String binaryString = StringUtils.leftPad(Integer.toBinaryString(byteValue & 0xff), 8, '0');
        System.out.println(binaryString);
    }

    @Test
    public void testPad() {
        String a = "abc";
        String b = "ab";
        System.out.println(StringUtils.center(a, 7, "*"));
    }

    @Test
    public void testGetTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        System.out.println(sdf.format(new Date()));
    }

    @Test
    public void testByteArr() {
        byte[] ba = {'%', 'w', 1, 3, 31};
        System.out.println(ba);
        for (byte b : ba) {
            System.out.printf("%02x", b);//257701031f
        }
    }

    @Test
    public void testHex2Byte() {
        System.out.println((byte) Integer.parseInt("31", 16));//31
    }

    @Test
    public void testByte2Binary() {
        byte b = -1;

        System.out.println(b & 0xFF);//255

        System.out.println(String.format("%8s",
                Integer.toBinaryString(b & 0xFF))
                .replace(' ', '0'));//11111111
    }

    @Test
    public void testByte2Str() {
        byte b = -86;

        System.out.println(new String(new byte[]{b}));
    }

    @Test
    public void testStr2Byte() {
        String str = "512";

        byte[] bytes = str.getBytes();
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }
    }

    @Test
    public void testByteToHex() {
        byte b = -86;
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        System.out.println(hex);
    }

    @Test
    public void testWrite2File() {
        byte[] data = {-86, -52, 2, 0, 2, 0, 9, 0, 1, 32, 16, 18, 20, 82, 0, 33, 18, 19, 0, 21};
        File file = new File("D://out.doo");
        try {
            OutputStream os = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(data);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            is.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWrite2FileMulti() {
        byte[] data = {-86, -52, 2, 0, 2, 0, 9, 0, 1, 32, 16, 18, 20, 82, 0, 33, 18, 19, 0, 21};
        File file = new File("D://out_multi.doo");
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(data);
            os.write("\r\n".getBytes());
            os.write(data);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadFromFile() {
        File file = new File("D://out.doo");
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

            System.out.println("Available bytes:" + in.available());

            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            in.close();

            byte[] content = out.toByteArray();
            System.out.println("Readed bytes count:" + content.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testReadMultiFromFile() {
        File file = new File("D://out_multi.doo");
        try {
            FileReader in = new FileReader(file);
            BufferedReader br = new BufferedReader(in);
            ArrayList<byte[]> arr = new ArrayList<>();
            String str;
            while ((str = br.readLine()) != null) {
                arr.add(str.getBytes());
            }
            in.close();
            br.close();

            System.out.println(arr.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
