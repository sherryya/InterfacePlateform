package com.db.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dao.ZdcAccountMapper;
import com.db.dto.ZdcAccount;
@Service
public class ZdcAccountService{
	@Autowired
	private ZdcAccountMapper zdcAccountMapper;	
	public ZdcAccount getAccountNameByTerminalID(String account_name)
	{
		return zdcAccountMapper.getAccountNameByTerminalID(account_name);
	}
}
