package com.pgz.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-21
 */
@Slf4j
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
        System.out.println((byte) Integer.parseInt("A5", 16));//31
        byte[] bytes = hex2Bytes(new String[]{
                "AA","AA","01","69","01","00","00","00","00","00","00","00","00","00","00","00","AA","CC","01","57","00","22","2B","07","01","15","01","13","10","0F","18","00","9E","00","00","00","00","00","00","00","00","07","D1","01","43","31","A4","00","00","00","00","00","00","00","00","00","00","00","00","00","01","01","00","00","01","04","07","02","00","0F","07","03","00","00","00","00","00","00","00","00","00","00","00","00","07","07","04","00","00","00","00","07","07","05","00","00","00","00","07","07","06","00","00","00","00","0F","07","11","00","00","00","00","00","00","00","00","00","00","00","00","07","07","12","00","00","00","00","07","07","13","00","00","00","00","07","07","14","00","00","00","00","0F","07","21","00","00","00","00","00","00","00","00","00","00","00","00","07","07","22","00","00","00","00","07","07","23","00","00","00","00","07","07","24","00","00","00","00","0F","07","31","00","00","00","00","00","00","00","00","00","00","00","00","07","07","32","00","00","00","00","07","07","33","00","00","00","00","07","07","34","00","00","00","00","0F","07","41","00","00","00","00","00","00","00","00","00","00","00","00","07","07","42","00","00","00","00","07","07","43","00","00","00","00","07","07","44","00","00","00","00","0F","07","51","00","00","00","00","00","00","00","00","00","00","00","00","07","07","52","00","00","00","00","07","07","53","00","00","00","00","07","07","54","00","00","00","00","0F","07","61","00","00","00","00","00","00","00","00","00","00","00","00","07","07","62","00","00","00","00","07","07","63","00","00","00","00","07","07","64","00","00","00","00","0F","07","71","00","00","00","00","00","00","00","00","00","00","00","00","07","07","72","00","00","00","00","07","07","73","00","00","00","00","07","07","74","00","00","00","00","0E","7A"
        });
        System.out.println(ArrayUtils.toString(bytes));
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
//        byte b = 74;
//        String hex = Integer.toHexString(b & 0xFF);
//        if (hex.length() < 2) {
//            hex = "0" + hex;
//        }
//        System.out.println(hex);

        System.out.println(ArrayUtils.toString(bytes2Hex(new byte[]{
                -86, -86, 0, 107, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -86, -52, 0, 89, 0, 2, 48, 8, 1, 86, 0, 0, 0, 0, 0, -73, 121, 53, 44, 30, 15, 0, -41, 0, 0, 0, -43, 0, 0, 0, -71, 0, 75, 111, 0, -38, -126, 0, 0, 0, -36, 0, 75, 0, 41, 0, 2, 0, -4, -54, 0, 14, 0, -124, 33, 8, 2, 0, -127, 0, 6, 0, -64, 0, -120, 0, 0, 85, 0, 106, 0, -25, 0, -98, 0, 1, 86, 0, 1, -31, 0, 50, 0, -53, 0, -47, 0, 19, -120, 21, -29
        })).replace(",", " "));
    }

    public static String[] bytes2Hex(byte[] data) {
        String[] hex = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            hex[i] = Integer.toHexString(data[i] & 0xFF).toUpperCase();
            if (hex[i].length() < 2) {
                hex[i] = "0" + hex[i];
            }
        }
        return hex;
    }

    public static String[] bytes2Hex(int[] data) {
        String[] hex = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            hex[i] = Integer.toHexString(data[i] & 0xFF).toUpperCase();
            if (hex[i].length() < 2) {
                hex[i] = "0" + hex[i];
            }
        }
        return hex;
    }

    public static byte[] hex2Bytes(String[] hex) {
        byte[] bytes = new byte[hex.length];
        for (int i = 0; i < hex.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex[i], 16);
        }
        return bytes;
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

    @Test
    public void testA() {
        System.out.println(1 << 4);
        System.out.println(1 << 3);
        System.out.println(1 << 2);
        System.out.println(1 << 1);
        System.out.println(1);
    }

    @Test
    public void testInt2ByteArray() {
        System.out.println(Arrays.toString(intToByteArray(512)));
        System.out.println(Arrays.toString(intToByteArray(512, 2)));
    }


    @Test
    public void testByteArray2Int() {
        System.out.println(byteArrayToInt(new byte[]{0x00, 0x03, 0x0D, -91}));
    }

    @Test
    public void testByteBuffer() {
        ByteBuffer buff = ByteBuffer.allocate(1024);
        String str = "helloWorld";
        buff.put(str.getBytes());
//        System.out.println(Arrays.toString(buff.array()));
        System.out.println(new String(buff.array()));
        System.out.println("position:" + buff.position() + "\t limit:"
                + buff.limit() + "\t capacity:" + buff.capacity());
        // 读取两个字节byte[] abytes = new byte[1];
        byte[] abytes = new byte[1];
        buff.get(abytes);
        System.out.println("get one byte to string:" + new String(abytes));
        // Reads the byte at this buffer's current position, and then increments
        // the position.
        buff.get();
        System.out.println("获取两个字节（两次get()方法调用）后");
        System.out.println("position:" + buff.position() + "\t limit:"
                + buff.limit());
        // Sets this buffer's mark at its position. like
        // ByteBuffer.this.mark=position
        buff.mark();
        System.out.println("mark()...");
        System.out.println("position:" + buff.position() + "\t limit:"
                + buff.limit());

        // 当读取到码流后，进行解码。首先对ByteBuffer进行flip操作，
        // 它的作用是将缓冲区当前的limit设置为position,position设置为0
        // flip方法将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，并将limit设置成之前position的值。
        buff.flip();
        System.out.println("flip()...");
        System.out.println("position:" + buff.position() + "\t limit:"
                + buff.limit() + "\t capacity:" + buff.capacity());

        byte[] tbyte = new byte[buff.limit()];
        buff.get(tbyte);
        System.out.println("get one byte to string:" + new String(tbyte));
        System.out.println("position:" + buff.position() + "\t limit:"
                + buff.limit());
        if (buff.hasRemaining()) {
            buff.compact();
        } else {
            buff.clear();
        }
    }

    /**
     * int转byte数组
     *
     * @param i
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-10-19
     **/
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        // 由高位到低位
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    /**
     * 指定字节位数
     *
     * @param value
     * @param digit
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-10-19
     **/
    public static byte[] intToByteArray(int value, int digit) {
        byte[] result = new byte[digit];
        // 由高位到低位
        for (int i = 0; i < digit; i++) {
            result[i] = (byte) ((value >> (8 * (digit - i - 1))) & 0xFF);
        }
        return result;
    }

    /**
     * 数组转int
     *
     * @param bytes
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-10-19
     **/
    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        int size = bytes.length;
        // 由高位到低位  
        for (int i = 0; i < size; i++) {
            int shift = (size - 1 - i) * 8;
            value += (bytes[i] & 0xFF) << shift;// 往高位游
        }
        return value;
    }

    @Test
    public void testSet() {
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        set.forEach(item -> {
            if (item.equals("a")) {
                item = "ad";
            }
        });
        System.out.println(set);
    }

    @Test
    public void testReplaceFirst() {
        String toHex = "90AF";
        System.out.println(toHex.charAt(0) == '0' ? toHex.substring(1) : toHex);
    }

    @Test
    public void testInteger1() {
        Integer a = 127, b = 128;

        System.out.println(127 == a);
        System.out.println(128 == b);
    }

    @Test
    public void testNanoTime() {
        long a = System.nanoTime();
        int sum = 0;
//        for (int i = 0; i < 100000; i++) {
//            sum += i;
//        }
//        System.out.println(sum + "");
        long b = System.nanoTime();
        System.out.println(b - a);
    }

    @Test
    public void testMatch() {
        String str = "0-a0aa_\"aa[ff1]";
        String ref = "[^a-z^A-Z0-9]";
        String s = Pattern.compile(ref).matcher(str).replaceAll("");
        System.out.println(s);

    }
}
