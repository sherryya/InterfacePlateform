package com.db.dao;

import java.util.List;

import com.db.dto.ZdcNewsInfo;

public interface ZdcNewsTotalUpdateMapper {
    /**
     * 新闻插入接口
     * 
     * @param zdcNewsInfo
     */
    void insert(ZdcNewsInfo zdcNewsInfo);

    void insertList(List<ZdcNewsInfo> zdcNewsInfo);

    /**
     * 新闻插入判断查询接口
     * 
     * @return
     */
    List<String> selectTime();

    /**
     * 1:基础版本:在当天固定时间Truncate滚动数据
     */
    void clear_date();

    /**
     * 清楚数据库中重复的新闻内容
     */
    void delete_alike_date();
}
