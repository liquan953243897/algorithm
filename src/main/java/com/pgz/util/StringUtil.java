package com.pgz.util;

/**
 * 字符串处理工具类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-05-27
 */
public class StringUtil {

    /**
     * 两个字符串分割合并拼接新的字符串
     * eg:
     * str1 = s1,s2
     * str2 = c1,c2
     * ret = s1c2，s2c2
     *
     * @param str1
     * @param str2
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-05-27
     **/
    public static String parallelJoin(String str1, String str2) {
        return parallelJoin(str1, str2, null, null);
    }

    /**
     * 两个字符串分割合并拼接新的字符串
     * eg:
     * str1 = s1,s2
     * str2 = c1,c2
     * ret = s1c2，s2c2
     *
     * @param str1
     * @param str2
     * @param regex
     * @param join
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-05-27
     **/
    public static String parallelJoin(String str1, String str2, String regex, String join) {
        StringBuilder sb = new StringBuilder();

        regex = regex == null ? "," : regex;
        join = join == null ? "，" : join;

        String[] arr1 = split(str1, regex);
        String[] arr2 = split(str2, regex);

        int arr1Size = arr1.length;
        int arr2Size = arr2.length;
        int max = Math.max(arr1Size, arr2Size);
        for (int i = 0; i < max; i++) {
            if (i < arr1Size) {
                sb.append(arr1[i]);
            }

            if (i < arr2Size) {
                sb.append(arr2[i]);
            }

            if (i < max - 1) {
                sb.append(join);
            }
        }
        return sb.toString();
    }

    /**
     * 分割字符串
     *
     * @param target
     * @param regex
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-05-27
     **/
    public static String[] split(String target, String regex) {

        if (target != null) {
            return target.split(regex);
        }

        return new String[]{};
    }

    public static void main(String[] args) {
        System.out.println(parallelJoin("天房,地房", "职务1,职务2"));

        System.out.println(parallelJoin("", ""));

        System.out.println(parallelJoin(null, null));

        System.out.println(parallelJoin("天房,地房", "职务1"));

        System.out.println(parallelJoin("天房", "职务1,职务2"));

        System.out.println(parallelJoin("天房,地房", null));

        System.out.println(parallelJoin(null, "职务1,职务2"));
    }

}
