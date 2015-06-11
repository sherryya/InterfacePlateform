package com.db.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.dao.ZdcGpsinfoOriginalMapper;
import com.db.dto.ZdcGpsinfoOriginal;
@Service
public class ZdcGpsinfoOriginalService{
	@Autowired
	private ZdcGpsinfoOriginalMapper  zdcGpsinfoOraginalMapper;
	//插入数据
	public int  insert(ZdcGpsinfoOriginal zdcGpsinfoOraginal)
	{
		return zdcGpsinfoOraginalMapper.insert(zdcGpsinfoOraginal);
	}
	//查询 未处理的信息
	public List<ZdcGpsinfoOriginal> select()
	{
		return zdcGpsinfoOraginalMapper.select();
	}
	//如果数据id已经处理好则更改is_deal状态
	public int updateGps(long id)
	{
		return zdcGpsinfoOraginalMapper.updateGps(id);
	}
	
	public void updateByPrimaryKeyIDS(List list)
	{
		zdcGpsinfoOraginalMapper.updateByPrimaryKeyIDS(list);
	}
}
