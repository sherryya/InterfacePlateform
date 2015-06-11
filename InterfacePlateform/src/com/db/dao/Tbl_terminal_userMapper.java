package com.db.dao;

import java.util.List;

import com.db.dto.Tbl_terminal_user;

public interface Tbl_terminal_userMapper {
	
	List<Tbl_terminal_user> selectDevices();
	
}
