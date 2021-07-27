package com.dwyinyuan.generate.utils;

import com.dwyinyuan.generate.model.pojo.ColumnEntity;
import com.dwyinyuan.generate.model.pojo.TableEntity;
import com.dwyinyuan.generate.model.pojo.TableInfoEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 公众号:笛舞音缘
 * @version 1.0
 * @description
 * @date 2021/7/27
 */
@Component
public class GenerateUtils {

    @Value("${package.name}")
    private String PACKAGE_NAME;

    @Value("${package.moduleName}")
    private String MODULE_NAME;

    @Value("${package.author}")
    private String AUTHOR;


    public List<String> templateList() {
        List<String> templateList = new ArrayList<>();
        templateList.add("template/Controller.java.vm");
        templateList.add("template/Mapper.java.vm");
        templateList.add("template/Service.java.vm");
        templateList.add("template/ServiceImpl.java.vm");
        templateList.add("template/Mapper.xml.vm");
        templateList.add("template/Entity.java.vm");
        return templateList;
    }

    /**
     * 生成代码
     *
     * @param tableInfoEntity 表的基本信息
     * @param columnEntities  表的字段信息
     * @param zip
     */
    public void generateCode(TableInfoEntity tableInfoEntity, List<ColumnEntity> columnEntities, ZipOutputStream zip) {
        Configuration configuration = getConfig();

        TableEntity tableEntity = new TableEntity();
        BeanUtils.copyProperties(tableInfoEntity, tableEntity);

        String attrNameCapital = tableNameToJavaName(tableEntity.getTableName());
        tableEntity.setAttrNameCapital(attrNameCapital);
        tableEntity.setAttrNameLowerCase(StringUtils.uncapitalize(attrNameCapital));
        List<ColumnEntity> columnEntityList = getColumnEntityList(columnEntities, configuration, tableEntity);

        tableEntity.setColumnEntityList(columnEntityList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPrimarykey() == null) {
            tableEntity.setPrimarykey(tableEntity.getColumnEntityList().get(0));
        }

        Properties properties = new Properties();
        properties.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(properties);
        String mainPath = configuration.getString("mainPath");
        mainPath = StringUtils.isBlank(mainPath) ? "io.renren" : mainPath;
        Map<String, Object> map = getStringObjectMap(tableEntity, mainPath);
        VelocityContext context = new VelocityContext(map);
        toZip(zip, tableEntity, context);
    }

    /**
     * 根据表的实体生成压缩包
     *
     * @param zip
     * @param tableEntity
     * @param context
     */
    private void toZip(ZipOutputStream zip, TableEntity tableEntity, VelocityContext context) {
        templateList().stream().peek(item -> {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(item, "UTF-8");
            tpl.merge(context, sw);
            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(item, tableEntity.getAttrNameCapital(), PACKAGE_NAME, MODULE_NAME)));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }).collect(Collectors.toList());
    }

    private List<ColumnEntity> getColumnEntityList(List<ColumnEntity> columnEntities, Configuration configuration, TableEntity tableEntity) {
        List<ColumnEntity> columnEntityList = columnEntities.stream().peek(item -> {
            String columnName = tableNameToJavaName(item.getColumnName());
            item.setAttrNameCapital(columnName);
            item.setAttrNameLowerCase(StringUtils.uncapitalize(columnName));

            String attrType = configuration.getString(item.getDataType());
            item.setAttrType(attrType);

            if ("PRI".equalsIgnoreCase(item.getColumnKey())) {
                tableEntity.setPrimarykey(item);
            }
        }).collect(Collectors.toList());
        return columnEntityList;
    }

    /**
     * 配置模板信息
     *
     * @param tableEntity
     * @param mainPath
     * @return
     */
    private Map<String, Object> getStringObjectMap(TableEntity tableEntity, String mainPath) {
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("tableComment", tableEntity.getTableComment());
        map.put("primarykey", tableEntity.getPrimarykey());
        map.put("attrNameCapital", tableEntity.getAttrNameCapital());
        map.put("attrNameLowerCase", tableEntity.getAttrNameLowerCase());
        map.put("pathName", tableEntity.getAttrNameLowerCase().toLowerCase());
        map.put("columnEntityList", tableEntity.getColumnEntityList());
        map.put("mainPath", mainPath);
        map.put("package", PACKAGE_NAME);
        map.put("moduleName", MODULE_NAME);
        map.put("author", AUTHOR);
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        return map;
    }


    /**
     * 将表名转换成Java实体类名
     *
     * @param tableName 表名
     */
    private String tableNameToJavaName(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            throw new NullPointerException("TableName can't empty or empty string !");
        }
        char[] charArray = tableName.toCharArray();
        StringBuffer builder = new StringBuffer(tableName.length());
        builder.append(charConversion(charArray[0]));
        for (int i = 1; i < charArray.length; i++) {
            if ('_' == charArray[i - 1] && i + 1 < charArray.length) {
                builder.append(charConversion(charArray[i]));
            } else {
                builder.append(charArray[i]);
            }
        }
        return builder.toString().replace("_", "");
    }


    /**
     * 使用位运算将小写字符转成大写。
     *
     * @param ch a~z
     * @return
     */
    public char charConversion(char ch) {
        if (ch <= 'a' && ch >= 'z') {
            return ch;
        }
        return (char) (ch ^ 32);
    }

    /**
     * 获取配置信息
     */
    public Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }
        if (template.contains("Entity.java.vm") || template.contains("MongoEntity.java.vm")) {
            return packagePath + File.separator + className + ".java";
        }

        if (template.contains("Mapper.java.vm")) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            String path = packagePath + "controller" + File.separator + className + "Controller.java";
            return path;
        }

        if (template.contains("Dao.xml.vm") || template.contains("Mapper.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Mapper.xml";
        }

        return null;
    }
}
