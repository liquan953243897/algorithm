package com.pgz.utils.translate.impl;

import com.pgz.utils.translate.interfaces.CacheDriver;
import com.pgz.utils.translate.interfaces.TranslatorDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 默认翻译驱动实现类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-07-04
 */
@Component("default")
public class DefaultTranslatorDriver implements TranslatorDriver {

    @Autowired
    private CacheDriver cacheDriver;

    /**
     * 定义存储结构
     * Map<String, Map<String, Object>
     *
     * @param codeType
     * @param code
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-07-25
     **/
    @Override
    public String translate(String codeType, String code) {
        Object o = cacheDriver.get(codeType);
        Map<String, Object> map = (Map<String, Object>) o;
        return (String) map.get(code);
    }
}
