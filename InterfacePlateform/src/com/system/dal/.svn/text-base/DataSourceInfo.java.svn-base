package com.system.dal;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据源信息
 * @author lugz
 *
 */
public class DataSourceInfo {
	@Autowired
	private DataSourceDesc dataSourceDesc;

	/**
	 * 数据源名称
	 */
	private String dataSourceName;
	
	/**
	 * 是否处于运行中。
	 */
	private boolean isRunning;
	
	/**
	 * 是否是在主库中运行
	 */
	private boolean isMaster;

	public DataSourceInfo(String dataSourceName, boolean isRunning, boolean isMaster) {
		this.dataSourceName = dataSourceName;
		this.isRunning = isRunning;
		this.isMaster = isMaster;
	}
	
	public DataSourceInfo() {
		this.dataSourceName = dataSourceDesc.getDefaultTargetDataSource();
		this.isRunning = true;
		this.isMaster = true;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}
	
}
