package com.dwyinyuan.generate.model.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author 公众号:笛舞音缘
 * @version 1.0
 * @description 表信息。
 * @date 2021/7/27
 */
@Data
public class TableEntity {

    /**
     * 表的名称
     */
    private String tableName;

    /**
     * 表的备注
     */
    private String tableComment;

    /**
     * 表主键
     */
    private ColumnEntity primarykey;

    /**
     * 表字段
     */
    private List<ColumnEntity> columnEntityList;

    /**
     * 类名(第一个字母大写)，如：sys_user => SysUser
     */
    private String attrNameCapital;

    /**
     * 类名(第一个字母小写)，如：sys_user => sysUser
     */
    private String attrNameLowerCase;
}
