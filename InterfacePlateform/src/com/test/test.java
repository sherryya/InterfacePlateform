package com.test;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import com.db.service.CommonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.system.SpringApplicationContextFactory;
public class test {
	public static final Logger logger = LoggerFactory.getLogger(test.class);
/*	private static ApplicationContext ctx = SpringApplicationContextFactory.newInstance();*/
	public static void main(String[] args) {

		
		String str="010102E9098304";
		System.out.println(str.substring(str.indexOf("2E"), str.length()));
	}
	public static synchronized boolean setCartMap(String sendIP,String ReceiveValue) {
/*		for (Map.Entry<String, Object> entry : checkMap.entrySet()) {
			if (null != map.get(entry.getKey())) {
				System.out.println("newMap:" + map.toString());
				return false;
			} else {
				map.put(entry.getKey(), entry.getValue());
			}
		}
		System.out.println("newMap:" + map.toString());*/
		// map.clear();
		
		return true;
	} 
	private static Map<String, Object> map = new HashMap<String, Object>();  
}
