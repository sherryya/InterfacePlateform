package com.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dao.ZdcNewsTotalUpdateMapper;
import com.db.dao.ZdcNewsUpdateMapper;
import com.db.dto.ZdcNewsInfo;

@Service
public class ZdcNewsTotalUpdateService {
    @Autowired
    private ZdcNewsTotalUpdateMapper newsUpdateMapper;

    public void insert(ZdcNewsInfo zdcNewsInfo) {
	newsUpdateMapper.insert(zdcNewsInfo);
    }

    public void insert(List<ZdcNewsInfo> zdcNewsInfo) {
	newsUpdateMapper.insertList(zdcNewsInfo);
    }
    
    public List<String> selectEndTimeSub() {
	return newsUpdateMapper.selectTime();
    }

    public void clearDataByTime() {
	newsUpdateMapper.clear_date();
    }

    public void delete() {
	newsUpdateMapper.delete_alike_date();
    }
}
