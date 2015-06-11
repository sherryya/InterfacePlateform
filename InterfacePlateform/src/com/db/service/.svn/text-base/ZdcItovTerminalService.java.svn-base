package com.db.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.db.dao.ZdcItovTerminalMapper;
import com.db.dto.ZdcItovTerminal;
@Service
public class ZdcItovTerminalService{
	@Autowired
	private ZdcItovTerminalMapper  zdcItovTerminalMapper;
	public List<ZdcItovTerminal>  selectAllForState( )
	{
		return zdcItovTerminalMapper.selectAllForState();
	}
}
