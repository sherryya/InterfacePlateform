package com.db.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dao.DbTimeMapper;
import com.db.dao.Tbl_terminal_userMapper;

@Service
public class CommonService{

	@Autowired
	private DbTimeMapper dbTimeMapper;
	@Autowired
	private Tbl_terminal_userMapper tbl_terminal_userMapper;
	
	public Date qryDate(){
		Date opTime = dbTimeMapper.selectDbTime().getDbTime();
		return opTime;
	}
	
	public List getDeviceList(){
		return tbl_terminal_userMapper.selectDevices();
	}
	
	
}
