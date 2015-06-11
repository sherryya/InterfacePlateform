package com.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dao.CarInfoMapper;
import com.db.dao.TZdcBreakruleqryFlagMapper;
import com.db.dao.TZdcBreakrulesMapper;
import com.db.dao.TZdcBreakrulescitylistMapper;
import com.db.dto.TItovCarVo;
import com.db.dto.TZdcBreakruleqryFlag;
import com.db.dto.TZdcBreakrules;
import com.db.dto.TZdcBreakrulescitylist;

/**
 * 违章记录service 20141128
 * @author Administrator
 *
 */
@Service
public class TzdcBreakRulesService {
	@Autowired
	private TZdcBreakrulesMapper tBreakRules;  //违章记录mapper
	@Autowired
	private CarInfoMapper carInfoMapper;
	
	@Autowired
	public TZdcBreakrulescitylistMapper tcitylistMapper;
	@Autowired
	private TZdcBreakruleqryFlagMapper tBreakRuleflag;  //违章记录mapper
	
	public int insert (TZdcBreakrules tzdcBreakRules)
	{
		return tBreakRules.insert(tzdcBreakRules);
	}
	/**
	 * 根据省份代码和城市代码查询是否需要发动机号和车架号信息
	 * @param cityList
	 * @return
	 */
	public List<TZdcBreakrulescitylist> selectByPcodeCity (TZdcBreakrulescitylist cityList)
	{
		return tcitylistMapper.selectByPcodeCity(cityList);
	}
	/**
	 * 插入城市列表
	 * @param cityList
	 * @return
	 */
	public int insert(TZdcBreakrulescitylist cityList)
	{
		return tcitylistMapper.insert(cityList);
	}
	/**
	 * 查询所有车辆
	 * @return
	 */
	public List<TItovCarVo> qryCarInfoAll()
	{
		return carInfoMapper.qryCarInfoAll();
	}
	/**
	 * 查询是否存在车辆违章记录根据车牌号和违章时间
	 * @return
	 */
	public int isExistBreakRule(TZdcBreakrules tzdcBreakRules)
	{
		return tBreakRules.isExistBreakRule(tzdcBreakRules);
	}
	
	/**
	 * 查询未阅读车辆违章信息的用户号码
	 * @return
	 */
	public List<String> qryTelList()
	{
		return tBreakRules.qryTelList();
	}
	/**
	 * 根据用户手机号查询用户的所有未阅读的违章记录
	 * @param account_name
	 * @return
	 */
	public List<TZdcBreakrules> qryBreakRuleListByTel(String account_name)
	{
		return tBreakRules.qryBreakRuleListByTel(account_name);
	}	

	/**
	 * 根据车牌号查询车辆是否查询
	 * @param car_plate_num
	 * @return
	 */
	public TZdcBreakruleqryFlag qryBreak(String car_plate_num)
	{
		return tBreakRuleflag.selectInfoByPlateNum(car_plate_num);
	}
	/**
	 * 插入车辆信息
	 * @param breakQryFlag
	 * @return
	 */
	public int insertqryBreak(TZdcBreakruleqryFlag breakQryFlag)
	{
		return tBreakRuleflag.insert(breakQryFlag);
	}
	/**
	 * 更新车牌查询违章的时间
	 * @param breakqryflag
	 * @return
	 */
	public int updateQryRecord(TZdcBreakruleqryFlag breakqryflag)
	{
		return tBreakRuleflag.updateQryRecord(breakqryflag);
	}
}
