package com.pgz.optdata;

/**
 * 操作数据
 *
 * @author liquan_pgz@qq.com
 * @date 2019-12-18
 */
public class OptData {

    /**
     * 用多条数据替换指定索引位置下的数据
     *
     * @param source 数据源数组
     * @param index  要替换的数组位置
     * @param target 替换的目标数据
     * @return 替换后的新数组
     * @author liquan_pgz@qq.com
     * date 2019-12-18
     **/
    public static Object[] replaceValue(Object[] source, int index, Object... target) {
        if (index < 0) {
            return source;
        }

        int newLength = source.length + target.length - 1;
        Object[] arr = new Object[newLength];
        for (int i = 0; i < newLength; ) {
            if (i < index) {
                arr[i] = source[i];
                i++;
            } else if (i == index) {
                for (Object o : target) {
                    arr[i] = o;
                    i++;
                }
            } else {
                arr[i] = source[i - target.length + 1];
                i++;
            }
        }
        return arr;
    }

}
