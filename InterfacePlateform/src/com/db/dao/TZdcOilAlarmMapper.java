package com.db.dao;

import java.util.List;

import com.db.dto.TZdcOilAlarm;

public interface TZdcOilAlarmMapper {
	
	/**
	 * 插入
	 * @param tzdcOil
	 * @return
	 */
	int insert(TZdcOilAlarm tzdcOil);
	/**
	 * 根据车机imei和油耗最低值查询最新一条数据
	 * @param tzdcOil
	 * @return
	 */
	TZdcOilAlarm selectByImeiOil(TZdcOilAlarm tzdcOil); 
	/**
	 * 根据车机imei删除此车机的记录
	 * @param terminalImei
	 * @return
	 */
	int deleteByImei(String terminalImei);
	/**
	 * 根据车机imei查询是否存在此账号的油耗信息
	 * @param terminalImei
	 * @return
	 */
	List<TZdcOilAlarm> selectByTerminalImei(String terminalImei);

}
