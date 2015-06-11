package com.db.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.db.dao.ZdcCanstreamOriginalMapper;
import com.db.dto.ZdcCanstreamOriginal;
@Service
public class ZdcCanstreamOriginalService{
	@Autowired
	private ZdcCanstreamOriginalMapper  zdcCanstreamOriginalMapper;
	public void  insert(ZdcCanstreamOriginal zdcCanstreamOriginal)
	{
		zdcCanstreamOriginalMapper.insert(zdcCanstreamOriginal);
	}
	public	List<ZdcCanstreamOriginal> select()
	{
		return zdcCanstreamOriginalMapper.select();
	}
	public  void updateByPrimaryKeySelective(long id)
	{
		zdcCanstreamOriginalMapper.updateByPrimaryKeySelective(id);
	}
	
	public  void updateByPrimaryKeySelectives(List list)
	{
		zdcCanstreamOriginalMapper.updateByPrimaryKeySelectives(list);
	}
}
