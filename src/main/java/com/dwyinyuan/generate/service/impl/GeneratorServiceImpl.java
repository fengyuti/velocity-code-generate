package com.dwyinyuan.generate.service.impl;

import com.dwyinyuan.generate.mapper.GeneratorMapper;
import com.dwyinyuan.generate.model.pojo.ColumnEntity;
import com.dwyinyuan.generate.model.pojo.TableInfoEntity;
import com.dwyinyuan.generate.service.GeneratorService;
import com.dwyinyuan.generate.utils.GenerateUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

/**
 * @author 公众号:笛舞音缘
 * @version 1.0
 * @description
 * @date 2021/7/27
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Resource
    private GeneratorMapper generatorMapper;

    @Autowired
    private GenerateUtils generateUtils;


    /**
     * 生成代码
     *
     * @param tableNames
     * @return
     */
    @Override
    public byte[] generate(String tableNames) throws ExecutionException, InterruptedException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(byteArrayOutputStream);
        for (String tableName : Arrays.stream(tableNames.split(",")).collect(Collectors.toList())) {
            TableInfoEntity tableInfoEntity = generatorMapper.selectByTableName(tableName);
            List<ColumnEntity> columnEntities = generatorMapper.selectTableInfo(tableName);
            generateUtils.generateCode(tableInfoEntity, columnEntities, zip);
        }
        IOUtils.closeQuietly(zip);
        return byteArrayOutputStream.toByteArray();
    }
}
