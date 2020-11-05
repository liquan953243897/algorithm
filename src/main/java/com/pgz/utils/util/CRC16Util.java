package com.pgz.utils.util;

import com.pgz.test.MyTest;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import java.util.zip.CRC32;

/**
 * CRC校验工具类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-10-19
 */
public class CRC16Util {

    /**
     * 获取源数据和验证码的组合byte数组
     *
     * @param strings 可变长度的十六进制字符串
     * @return
     */
    public static byte[] appendCrc16(String... strings) {
        byte[] data = new byte[]{};
        for (String string : strings) {
            int x = Integer.parseInt(string, 16);
            byte n = (byte) x;
            byte[] buffer = new byte[data.length + 1];
            byte[] aa = {n};
            System.arraycopy(data, 0, buffer, 0, data.length);
            System.arraycopy(aa, 0, buffer, data.length, aa.length);
            data = buffer;
        }
        return appendCrc16(data);
    }

    /**
     * 获取源数据和验证码的组合byte数组
     *
     * @param aa 字节数组
     * @return
     */
    public static byte[] appendCrc16(byte[] aa) {
        byte[] bb = getCrc16(aa);
        byte[] cc = new byte[aa.length + bb.length];
        System.arraycopy(aa, 0, cc, 0, aa.length);
        System.arraycopy(bb, 0, cc, aa.length, bb.length);
        return cc;
    }

    public static void main(String[] args) {
        System.out.println(ArrayUtils.toString(appendCrc16(new byte[]{84, 69, 83, 84})));
        byte[] byteCRC = get4byteCRC(new byte[]{84, 69, 83, 84});
        System.out.println(ArrayUtils.toString(byteCRC));
        System.out.println(MyTest.byteArrayToInt(byteCRC));
    }

    /**
     * 获取验证码byte数组，基于Modbus CRC16的校验算法
     */
    public static byte[] getCrc16(byte[] arr_buff) {
        int len = arr_buff.length;

        // 预置 1 个 16 位的寄存器为十六进制FFFF, 称此寄存器为 CRC寄存器。
        int crc = 0xFFFF;
        int i, j;
        for (i = 0; i < len; i++) {
            // 把第一个 8 位二进制数据 与 16 位的 CRC寄存器的低 8 位相异或, 把结果放于 CRC寄存器
            crc = ((crc & 0xFF00) | (crc & 0x00FF) ^ (arr_buff[i] & 0xFF));
            for (j = 0; j < 8; j++) {
                // 把 CRC 寄存器的内容右移一位( 朝低位)用 0 填补最高位, 并检查右移后的移出位
                if ((crc & 0x0001) > 0) {
                    // 如果移出位为 1, CRC寄存器与多项式A001进行异或
                    crc = crc >> 1;
                    crc = crc ^ 0xA001;
                } else
                    // 如果移出位为 0,再次右移一位
                    crc = crc >> 1;
            }
        }

        return intToBytes(crc);
    }

    /**
     * 将int转换成byte数组，低位在前，高位在后
     * 改变高低位顺序只需调换数组序号
     */
    private static byte[] intToBytes(Integer value) {
        byte[] src = new byte[4];
        src[3] = (byte) (value & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[1] = (byte) (value >> 16 & 0xff);
        src[0] = (byte) (value >> 24 & 0xff);
        return src;
    }

    public static byte[] get4byteCRC(byte[] buffer){
        CRC32 crc32 = new CRC32();
        crc32.update(buffer);
        byte[] mCrc32Buf = new byte[2];
        mCrc32Buf[0] = (byte)(crc32.getValue() & 0x000000FF);
        mCrc32Buf[1] = (byte)((crc32.getValue() & 0x0000FF00) >> 8);
        return mCrc32Buf;
    }

    public static byte[] get2ByteCRC(byte[] buffer){
        int sum = 0;
        for (byte b : buffer) {
            sum += b;
        }

        return intToBytes(sum);
    }

    @Test
    public void testInt2Bytes() {
        byte[] bytes = get2ByteCRC(new byte[]{-86,-52,0,34,0,3,9,0,1,32,16,18,20,82,0,6,0,2,33,18,19,11,7,1,-1,-1,-1,-1,0,0,0,0});
        System.out.println(ArrayUtils.toString(bytes));
    }
}
