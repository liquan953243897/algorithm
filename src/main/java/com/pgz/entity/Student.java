package com.pgz.entity;

import com.pgz.utils.translate.annotation.TranslateAtt;
import lombok.Data;

/**
 * 学生实体
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-25
 */
@Data
public class Student {

    private String name;

    private int age;

    @TranslateAtt(codeType = "sex")
    private String sex;
}
