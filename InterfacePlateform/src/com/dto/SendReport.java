package com.dto;

public class SendReport {

	String sendType="0";//发送类型 ：  0)  客户端主动发送数据     2)广播式发送数据  服务器端发起   1) 服务器指定客户端发送  服务器端发起
	String sendIP="";// 客户端IP  服务器端指定客户端发送时用到
	String sendContent="";//发送的内容
	String sendHead="";//发送头  GPS  CAN
	String sendTerminal="";//发送终端　　　TEL　手机　　　CAR　车机
	
	public String getSendTerminal() {
		return sendTerminal;
	}
	public void setSendTerminal(String sendTerminal) {
		this.sendTerminal = sendTerminal;
	}
	public String getSendHead() {
		return sendHead;
	}
	public void setSendHead(String sendHead) {
		this.sendHead = sendHead;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getSendIP() {
		return sendIP;
	}
	public void setSendIP(String sendIP) {
		this.sendIP = sendIP;
	}
	public String getSendContent() {
		return sendContent;
	}
	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}
	
}
