package com.dwyinyuan.generate.model.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author 公众号:笛舞音缘
 * @version 1.0
 * @description 表信息。
 * @date 2021/7/27
 */
@Data
public class TableInfoEntity {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表的引擎
     */
    private String engine;

    /**
     * 备注
     */
    private String tableComment;

    /**
     * 创建时间
     */
    private Date createTime;
}
