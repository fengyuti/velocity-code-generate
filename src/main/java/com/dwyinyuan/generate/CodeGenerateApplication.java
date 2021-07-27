package com.dwyinyuan.generate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = {"com.dwyinyuan.generate.mapper"})
@SpringBootApplication
public class CodeGenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeGenerateApplication.class, args);
    }

}
