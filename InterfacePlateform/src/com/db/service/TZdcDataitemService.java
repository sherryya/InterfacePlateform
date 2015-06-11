package com.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dao.TZdcDataitemMapper;
import com.db.dao.TZdcOilAlarmMapper;
import com.db.dao.TZdcTerminalOnlineflagMapper;
import com.db.dto.TZdcDataitem;
import com.db.dto.TZdcOilAlarm;
import com.db.dto.TZdcTerminalOnlineflag;

@Service
public class TZdcDataitemService {
	@Autowired
	private TZdcDataitemMapper tdataMapper;
	
	@Autowired
	private TZdcOilAlarmMapper oilMapper;
	
	@Autowired
	private TZdcTerminalOnlineflagMapper terminalMapper;
	
	/**
	 * 根据字典名称查询字典值
	 * @param itemId
	 * @return
	 */
	public TZdcDataitem selectByPrimaryKey(String itemId)
	{
		return tdataMapper.selectByPrimaryKey(itemId);
	}
	
	/**
	 * 根据车机imei和油耗最低值查询最新一条数据
	 * @param tzdcOil
	 * @return
	 */
	public TZdcOilAlarm selectByImeiOil(TZdcOilAlarm tzdcOil)
	{
		return oilMapper.selectByImeiOil(tzdcOil);
	}
	
	/**
	 * 插入
	 * @param tzdcOil
	 * @return
	 */
	public int insert(TZdcOilAlarm tzdcOil)
	{
		return oilMapper.insert(tzdcOil);
	}
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
	 * 根据车机imei删除此车机的记录
	 * @param terminalImei
	 * @return
	 */
	public int deleteByImei(String terminalImei)
	{
		return oilMapper.deleteByImei(terminalImei);
	}
	
	/**
	 * 根据车机imei查询是否存在此账号的油耗信息
	 * @param terminalImei
	 * @return
	 */
	public List<TZdcOilAlarm> selectByTerminalImei(String terminalImei)
	{
		return oilMapper.selectByTerminalImei(terminalImei);
	}


}
