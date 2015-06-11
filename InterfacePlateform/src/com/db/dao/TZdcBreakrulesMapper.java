package com.db.dao;
import java.util.List;

import com.db.dto.TZdcBreakrules;


public interface TZdcBreakrulesMapper {
	
	/**
	 * 得到违章驾驶记录
	 * @param account_id  用户手机端ID
	 * @return
	 */
	List<TZdcBreakrules> getBreakrulesList(int account_id);
	/**
	 * 插入违章记录信息
	 * @param tzdcBreakRules
	 * @return
	 */
	int insert(TZdcBreakrules tzdcBreakRules);
	/**
	 * 查询是否存在车辆违章记录
	 * @return
	 */
	int isExistBreakRule(TZdcBreakrules tzdcBreakRules);
	/**
	 * 查询未阅读车辆违章信息的用户号码
	 * @return
	 */
	List<String> qryTelList();
	/**
	 * 根据用户手机号查询用户的所有未阅读的违章记录
	 * @param account_name
	 * @return
	 */
	List<TZdcBreakrules> qryBreakRuleListByTel(String account_name);
	

}
