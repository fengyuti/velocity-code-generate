package com.dwyinyuan.generate.mapper;

import com.dwyinyuan.generate.model.pojo.ColumnEntity;
import com.dwyinyuan.generate.model.pojo.TableInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 公众号:笛舞音缘
 * @version 1.0
 * @description
 * @date 2021/7/27
 */
@Mapper
public interface GeneratorMapper {

    /**
     * 根据表名查询表信息
     *
     * @param tableName
     * @return
     */
    TableInfoEntity selectByTableName(@Param("tableName") String tableName);

    List<ColumnEntity> selectTableInfo(@Param("tableName") String tableName);
}
