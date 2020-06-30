package com.pgz.utils.translate.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 翻译工具类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-06-23
 */
@Component
public final class TranslateUtil implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
