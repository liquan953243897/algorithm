package com.pgz.utils.translate.impl;

import com.pgz.utils.translate.constant.MapCache;
import com.pgz.utils.translate.interfaces.CacheDriver;
import org.springframework.stereotype.Service;

/**
 * 自定义默认缓存器实现类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-07-25
 */
@Service
public class DefaultCacheDriver implements CacheDriver {

    @Override
    public Object get(String key) {
        return MapCache.MAP_CACHE.get(key);
    }

    @Override
    public void set(String key, Object value) {
        MapCache.MAP_CACHE.put(key, value);
    }
}
