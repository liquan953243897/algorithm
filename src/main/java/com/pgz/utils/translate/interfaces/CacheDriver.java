package com.pgz.utils.translate.interfaces;

/**
 * 缓存驱动器
 *
 * @author liquan_pgz@qq.com
 * @date 2020-06-23
 */
public interface CacheDriver {

    Object get(String key);

    void set(String key, Object value);
}
