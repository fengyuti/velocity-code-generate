package com.dwyinyuan.generate.service;

import java.util.concurrent.ExecutionException;

/**
 * @author 公众号:笛舞音缘
 * @version 1.0
 * @description
 * @date 2021/7/27
 */
public interface GeneratorService {

    byte[] generate(String tableNames) throws ExecutionException, InterruptedException;
}
