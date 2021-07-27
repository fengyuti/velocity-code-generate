package com.dwyinyuan.generate.base;

import java.util.List;

public interface BaseService<T> {

    T selectByKey(Object key);
    
    T selectOne(T entity);

    int selectCount(T entity);

    int save(T entity);

    int delete(Object key);

    int updateAll(T entity);

    int updateNotNull(T entity);

    List<T> selectPage(int pageNum, int pageSize);

    List<T> selectPage(int pageNum, int pageSize, T entity);
    
}