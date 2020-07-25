package com.pgz.utils.translate.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Map缓存结构
 *
 * @author liquan_pgz@qq.com
 * @date 2020-07-25
 */
public interface MapCache {

    Map<String, Object> MAP_CACHE = new HashMap<String, Object>() {
        {
            put("sex", new HashMap<String, Object>() {
                {
                    put("0", "男");
                    put("1", "女");
                }
            });
        }
    };

}
