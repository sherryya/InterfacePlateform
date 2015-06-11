package com.db.dao;
import java.util.List;
import com.db.dto.ZdcCanstreamOriginal;
public interface ZdcCanstreamOriginalMapper {
	void  insert(ZdcCanstreamOriginal zdcCanstreamOriginal);
	 List<ZdcCanstreamOriginal> select();
	 void updateByPrimaryKeySelective(long id);
	 void updateByPrimaryKeySelectives(List list);
}
