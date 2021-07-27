package com.dwyinyuan.generate.model.pojo;

import lombok.Data;

/**
 * @author 公众号:笛舞音缘
 * @version 1.0
 * @description 表中字段的属性
 * @date 2021/7/27
 */
@Data
public class ColumnEntity {

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 字段名类型
     */
    private String dataType;

    /**
     * 字段名备注
     */
    private String columnComment;

    /**
     * 字段名(第一个字母大写)如：user_name => UserName
     */
    private String attrNameCapital;

    /**
     * 字段名(第一个字母小写)，如：user_name => userName
     */
    private String attrNameLowerCase;

    /**
     * 字段类型
     */
    private String attrType;

    private String columnKey;

    private String extra;
}
