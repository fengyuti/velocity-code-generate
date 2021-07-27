package com.dwyinyuan.generate.controller;

import com.dwyinyuan.generate.model.dto.ResponseResult;
import com.dwyinyuan.generate.service.GeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author 公众号:笛舞音缘
 * @version 1.0
 * @description
 * @date 2021/7/27
 */
@Api(value = "代码生成接口文档", tags = "代码生成")
@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;


    /**
     * 生成
     *
     * @param tableNames 表名集合
     * @param response
     */
    @ApiOperation(value = "生成代码", tags = "")
    @GetMapping("/generate")
    public ResponseResult generate(@RequestParam("tableNames") String tableNames, HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {

        byte[] generate = generatorService.generate(tableNames);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"java_generator.zip\"");
        response.addHeader("Content-Length", "" + generate.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(generate, response.getOutputStream());
        return ResponseResult.success();
    }
}
