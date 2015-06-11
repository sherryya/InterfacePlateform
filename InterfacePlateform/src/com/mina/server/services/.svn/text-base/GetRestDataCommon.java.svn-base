package com.mina.server.services;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.bean.reqeustParam;
import com.rest.bean.requestHeader;
import com.rest.util.ConnUtil;
import com.rest.util.PropertiesUtil;
public class GetRestDataCommon {
	public static String remoteServer = "http://172.16.1.14:8189/zdc/";
	public static String appKey = "sshop";
	public static String signKey = "1qazxsw2";
	public static int appid = 1;
	public static int clientPlatform = 1;
	public static String jsonParam(@SuppressWarnings("rawtypes") Map param) throws Exception{
		requestHeader rh = new requestHeader();
		rh.setAppid(appid);
		rh.setCommand_id(1000);//
		rh.setPlatform(clientPlatform);
		rh.setScreenX(1024);//
		rh.setScreenY(769);//
		rh.setTerm_id("111111");//
		rh.setTimestamp();//
		rh.setUser_id(1);//
		reqeustParam rp = new reqeustParam();
		rp.setHead(rh);
		rp.setBody(param);
		return new ObjectMapper().writeValueAsString(rp);
	}
	public  void getRestDataCommon(@SuppressWarnings("rawtypes") Map paramMap,String server_name) throws Exception{
		
		String filePath = this.getClass().getClassLoader().getResource("remoteServer.properties").toString().substring(6);;
		filePath = "/"+filePath;
		remoteServer=PropertiesUtil.readValue(filePath, "remoteServer");
		String result = ConnUtil.sendPostZip(
				remoteServer, 
				server_name,
				jsonParam(paramMap), 
				appKey, //appkey
				signKey//signKey
				);
		System.out.println("~~~~~~~~~~~~~~~"+result+"~~~~~~~~~~~~~~");
		try {
			ConnUtil.validRequestParam(result, signKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(result);
		JsonNode headNode = jsonNode.get("head");
		JsonNode bodyNode = jsonNode.get("body");
		System.out.println("jsonNode:"+jsonNode);
		System.out.println("header:"+headNode);
		System.out.println("body:"+bodyNode);
	}
}
