package com.system.dal;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

public class EventRoutingInterceptor{
	@Autowired
	private DataSourceDesc dataSourceDesc;
	
	/**
	 * 路由数据库。
	 * @param jp
	 */
	public void route(JoinPoint jp)  
	{
		DataSourceInfo dataSourceInfo = DataSourceContextHolder.getDataSource();
		String funcName = jp.getSignature().getName();
		boolean isQuery = funcName.matches("(find|get|qry|select)(.*)");
		
		//第一次访问service时，dataSourceInfo为null。
		if (dataSourceInfo == null) {
			if (isQuery) {
				Integer nextCounter = DataSourceContextHolder.getNextCounter();
				nextCounter = nextCounter % dataSourceDesc.getTargetDataSources().size();
				dataSourceInfo = new DataSourceInfo(
						nextCounter.toString(), //dataSourceName
						true, //isRunning
						false  //isMaster
						);
			} else {
				dataSourceInfo = new DataSourceInfo(
						dataSourceDesc.getDefaultTargetDataSource(), //dataSourceName
						true, //isRunning
						true  //isMaster
						);
			}
			DataSourceContextHolder.setDataSource(dataSourceInfo);
		} else {
			if (isQuery) {
				//不做处理，走原来的链接。
			} else {
				if (dataSourceInfo.isMaster()) {
					//任然使用Master库，不做调整。
				} else {
					//清除原来的数据源。
					DataSourceContextHolder.clearDataSource();

					//把原来的读数据库源，调整为Master。
					dataSourceInfo = new DataSourceInfo(
							dataSourceDesc.getDefaultTargetDataSource(), //dataSourceName
							true, //isRunning
							true  //isMaster
							);
					DataSourceContextHolder.setDataSource(dataSourceInfo);
				}
			}
		}
	}  
}
