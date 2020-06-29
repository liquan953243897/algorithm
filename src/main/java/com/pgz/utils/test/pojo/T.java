package com.pgz.utils.test.pojo;

import com.pgz.utils.annotation.FieldMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liquan_pgz@qq.com
 * @date 2020-06-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class T {

    @FieldMapper(kv = {"1:男", "0=女"}, fieldName = "kv")
    private String kv1;

    @FieldMapper(format = "yyyy-MM-dd HH:mm:ss", fieldName = "date")
    private String date1;

    @FieldMapper(fieldName = "other")
    private String other1;

    @FieldMapper(format = "yyyy-MM-dd HH:mm:ss", fieldName = "dateS")
    private Date dateD;
}
