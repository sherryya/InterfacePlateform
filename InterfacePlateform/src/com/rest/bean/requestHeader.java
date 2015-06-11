package com.rest.bean;

import java.util.Date;

public class requestHeader {
	
	private int appid;
	private int platform;
	private int screenX;
	private int screenY;
	private String term_id;
	private int command_id;
	private long timestamp;
	private long user_id;

	public requestHeader() {

	}	
	public int getAppid() {
		return appid;
	}
	public void setAppid(int appid) {
		this.appid = appid;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public int getScreenX() {
		return screenX;
	}
	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}
	public int getScreenY() {
		return screenY;
	}
	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	public String getTerm_id() {
		return term_id;
	}
	public void setTerm_id(String term_id) {
		this.term_id = term_id;
	}
	public int getCommand_id() {
		return command_id;
	}
	public void setCommand_id(int command_id) {
		this.command_id = command_id;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp() {
		this.timestamp = new Date().getTime();
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
}
