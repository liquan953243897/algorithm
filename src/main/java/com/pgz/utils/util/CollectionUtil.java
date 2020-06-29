package com.pgz.utils.util;

import cn.hutool.core.util.ObjectUtil;

import java.util.List;

/**
 * 集合处理工具类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-06-28
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * 集合按指定对象列表顺序规则进行排序
     *
     * @param orderRegulation 排序规则集合
     * @param targetList      目标集合
     * @param fun             函数
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-06-29 10:11:06
     **/
    public static <T> void sort(List<Object> orderRegulation,
                                List<T> targetList, Function<T> fun) {
        if (ObjectUtil.isEmpty(targetList)) return;

        targetList.sort(((o1, o2) -> {
            int io1 = orderRegulation.indexOf(fun.get(o1));
            int io2 = orderRegulation.indexOf(fun.get(o2));

            if (io1 != -1) {
                io1 = targetList.size() - io1;
            }
            if (io2 != -1) {
                io2 = targetList.size() - io2;
            }

            return io2 - io1;
        }));
    }

    public interface Function<T> {
        Object get(T t);
    }
}
