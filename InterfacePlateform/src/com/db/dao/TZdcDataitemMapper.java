package com.db.dao;

import com.db.dto.TZdcDataitem;

public interface TZdcDataitemMapper {
	/**
	 * 根据字典名称查询字典值
	 * @param itemId
	 * @return
	 */
	TZdcDataitem selectByPrimaryKey(String itemId);

}
