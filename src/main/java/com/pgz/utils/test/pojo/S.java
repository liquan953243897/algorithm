package com.pgz.utils.test.pojo;

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
public class S {

    private String kv;

    private Date date;

    private String other;

    private String dateS;
}
