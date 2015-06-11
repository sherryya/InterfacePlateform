package com.db.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.db.dao.TZdcTerminalOnlineflagMapper;
import com.db.dto.TZdcTerminalOnlineflag;

@Service
public class TZdcTerminalOnlineflagService {

	
	@Autowired
	private TZdcTerminalOnlineflagMapper terminalMapper;
	
	/**
	 * 根据车机imei查询车辆是否在线
	 * @param terminalImei
	 * @return
	 */
	public TZdcTerminalOnlineflag selectInfoByImei(String terminalImei)
	{
		return terminalMapper.selectInfoByImei(terminalImei);
	}

	
	/**
	 * 根据车机imei查询是否存在此账号的油耗信息
	 * @param terminalImei
	 * @return
	 */


	public int insert(TZdcTerminalOnlineflag tZdcTerminalOnlineflag)

	{
		return terminalMapper.insert(tZdcTerminalOnlineflag);
	}

	public int update(TZdcTerminalOnlineflag tZdcTerminalOnlineflag)
	{
		return terminalMapper.update(tZdcTerminalOnlineflag);
	}
}
