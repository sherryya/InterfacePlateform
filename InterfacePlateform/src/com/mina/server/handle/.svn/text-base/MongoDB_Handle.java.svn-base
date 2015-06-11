package com.mina.server.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crontab.util.DateUtil;
import com.dto.SendReport;
import com.mina.server.Initialization;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteConcern;

public class MongoDB_Handle {
	private    Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	/**
	 * CAN原始数据写入mongodb
	 * 
	 * @param sendReport
	 */
	public static void can_insert_mongodb(SendReport sendReport) {
		try {
			// log.info("================ CAN 原始数据  入mongodb 开始=====================");
			String date = DateUtil.getDateyyyyMMDD();
			// log.info("================ CAN 原始数据  入mongodb date====================="+date);
			DBCollection collection = Initialization.getInstance().getDb().getCollection("t_zdc_canstream_original" + date);
			BasicDBObject bo = new BasicDBObject();
			bo.put("deviceuid", sendReport.getSendIP());
			bo.put("Content", sendReport.getSendContent());
			bo.put("is_deal", 0);
			collection.insert(bo);
			//collection.setWriteConcern(WriteConcern.SAFE);
			// log.info("================ CAN 原始数据 入mongodb 结束=====================");
		} catch (Exception e) {
			// TODO: handle exception
			new MongoDB_Handle().logger.info("CAN原始数据写入mongodb失败-->"+e.getMessage());
		}
	}
}
