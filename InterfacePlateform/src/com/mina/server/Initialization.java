package com.mina.server;

import java.net.UnknownHostException;
import java.util.HashMap;
import org.apache.mina.core.session.IoSession;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class Initialization {
	private static Initialization instance;
	private HashMap<String, IoSession> clientMap;
	private Mongo mongo;
	private DB db;
	private boolean connectFlag=false;
	public boolean isConnectFlag() {
		return connectFlag;
	}

	public void setConnectFlag(boolean connectFlag) {
		this.connectFlag = connectFlag;
	}

	public Initialization() {

	}

	public Mongo getMongo() {
		return mongo;
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public static Initialization getInstance() {
		if (instance == null)
			instance = new Initialization();
		return instance;
	}

	public void init() throws UnknownHostException {
		HashMap<String, IoSession> clientMap = new HashMap<String, IoSession>();
		Mongo mongo;
		DB db;
		boolean connectFlag;
		this.clientMap = clientMap;
		mongo = new Mongo("222.171.242.178", 10900);
		//mongo = new Mongo("172.16.1.101", 30000);
		//mongo.getDB("admin");
		//db = mongo.getDB("admin");
		mongo = new Mongo("222.171.242.178", 10900);
		//mongo = new Mongo("172.16.1.101", 30000);

		connectFlag = mongo.getDB("admin").authenticate("jthx", "123456".toCharArray());
		db = mongo.getDB("jthx");
		this.mongo=mongo;
		this.db=db;
		this.connectFlag=connectFlag;
				
		
	}

	public HashMap<String, IoSession> getClientMap() {
		return clientMap;
	}

	public void setClientMap(HashMap<String, IoSession> clientMap) {
		this.clientMap = clientMap;
	}

}
