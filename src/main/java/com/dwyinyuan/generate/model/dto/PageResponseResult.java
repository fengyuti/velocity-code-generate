package com.dwyinyuan.generate.model.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 公众号:笛舞音缘
 * @version 1.0
 * @description
 * @date 2021/7/27
 */
@Data
public class PageResponseResult implements Serializable {
    //总记录数
    private int totalCount;
    //每页记录数
    private int pageSize;
    //总页数
    private int totalPage;
    //当前页数
    private int currPage;
    //列表数据
    private List<?> list;

    public PageResponseResult(int pageSize, List<?> list) {
        PageInfo<?> tPageInfo = new PageInfo(list, pageSize);
        this.totalCount = (int) tPageInfo.getTotal();
        this.pageSize = tPageInfo.getPageSize();
        this.totalPage = tPageInfo.getPages();
        this.currPage = tPageInfo.getSize() - 1;
        this.list = tPageInfo.getList();
    }
}
