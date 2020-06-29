package com.pgz.utils.test;

import cn.hutool.core.lang.Console;
import com.pgz.utils.test.pojo.S;
import com.pgz.utils.test.pojo.T;
import com.pgz.utils.util.BeanMapper;
import org.junit.Test;

import java.util.Date;

/**
 * BeanMapper测试类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-06-29
 */
public class BeanMapperTest {

    @Test
    public void testBeanMapper() {
        S s = new S("1", new Date(), "其他信息", "2019-10-03 12:15:47");

        T t = BeanMapper.beanMapper(s, T.class);

        Console.print(t);
    }


}
