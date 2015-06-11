package com.mina.server;

import java.io.IOException;
import java.net.UnknownHostException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongDBUtil {
	/**
	 * 将用户名和sessionID对应上   如果用户已经存在  那么就修改
	 * @param sendUser   用户名
	 * @param sessionID  新的sessionID 
	 * @throws UnknownHostException
	 */
	public static void WriteMongodb(String sendUser,String sessionID,String sendType,String sendTerminal) throws UnknownHostException
	{
		DB db;
		boolean connectFlag;
		Initialization init = Initialization.getInstance();
		db = init.getDb();
		connectFlag =init.isConnectFlag();
		if (connectFlag) {
			DBCollection collection = db.getCollection("MinaLongConnectInfo");
			String flag="0";
			if(sendType.equals("0"))//中有终端重连的时候才 修改 sessionID
			{
				BasicDBObject condition = new BasicDBObject();
				condition.put("sendUser", sendUser);
				condition.put("sendTerminal", sendTerminal);
				DBCursor dbcursor = collection.find(condition);
				while (dbcursor.hasNext()) {//如果用户已经存在，需要根据用户名  修改sessionID
					String dbcursor_message = dbcursor.next().toString();
					System.out.println(dbcursor_message);
					DBObject updateCondition=new BasicDBObject();
					updateCondition.put("sendUser", sendUser);
					updateCondition.put("sendTerminal", sendTerminal);
					
					DBObject updatedValue=new BasicDBObject();
					updatedValue.put("sessionID", sessionID);
					DBObject updateSetValue=new BasicDBObject("$set",updatedValue);
					collection.update(updateCondition, updateSetValue);
					flag="1";
				}
				if(flag.equals("0"))  //如果没有 则插入 mongodb
				{
					BasicDBObject bo = new BasicDBObject();
					bo.put("sendUser", sendUser);
					bo.put("sessionID", sessionID);
					bo.put("sendTerminal", sendTerminal);
					collection.insert(bo);
				}
			}

		}
	}
	/**
	 * 根据用户名得到用户的 sessionID
	 * @param sendUser
	 * @return
	 * @throws UnknownHostException
	 */
	public static String GetMongodb(String sendUser,String sendTerminal) throws UnknownHostException {
		DB db;
		boolean connectFlag;

		Initialization init = Initialization.getInstance();
		db = init.getDb();
		connectFlag = init.isConnectFlag();
		String dbcursor_message = "";
		String sessionID = "";
		if (connectFlag) {
			DBCollection collection = db.getCollection("MinaLongConnectInfo");
			BasicDBObject condition = new BasicDBObject();
			condition.put("sendUser", sendUser);
			condition.put("sendTerminal", sendTerminal);
			DBCursor dbcursor = collection.find(condition);
			while (dbcursor.hasNext()) {
				dbcursor_message = dbcursor.next().toString();
				System.out.println(dbcursor_message);
				ObjectMapper objectmapper = new ObjectMapper();
				try {
					JsonNode dbcursorJson = objectmapper.readTree(dbcursor_message);
					sessionID = dbcursorJson.get("sessionID").toString();
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sessionID;
	}
}
