package com.mina.server;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dto.SendReport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * 继承自IoHandlerAdapter，IoHandlerAdapter继承接口 IoHandler
 * 类IoHandlerAdapter实现了IoHandler的所有方法，只要重载关心的几个方法就可以了
 */
public class CopyOfMinaServerHandler extends IoHandlerAdapter {
	private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
	AutoDealUploadData autoDealUploadData = new AutoDealUploadData();
	private String buffer = "";
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
		logger.warn(cause.getMessage(), cause);
		logger.info("客户端发送信息异常:session-->" + session.getRemoteAddress().toString());
		session.close(true);
	}
	/*
	 * 这个方法是目前这个类里最主要的， 当接收到消息，只要不是quit，就把服务器当前的时间返回给客户端 如果是quit，则关闭客户端连接
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String str = message.toString();
		if (str.trim().equalsIgnoreCase("quit")) {
			session.close();
			return;
		}
		//logger.info("接收内容=================================：" + str);
		SendReport sendReport = new SendReport();
		try {
			Type listType = new TypeToken<SendReport>() {
			}.getType();
			Gson gson = new Gson();
			sendReport = gson.fromJson(str, listType);
			//System.out.println(sendReport.getSendContent());
			//System.out.println(sendReport.getSendIP());
			//System.out.println(sendReport.getSendType());
			//System.out.println(sendReport.getSendHead());
			//System.out.println(sendReport.getSendTerminal());
			Date date = new Date();
			String SendIP = "";
			//logger.info(("发送内容：" + str + "；发送对象：" + session.getRemoteAddress() + "；发送时间:" + date.toString()));
/*			if (sendReport.getSendType().equalsIgnoreCase("0"))// 只有终端发送的时候
																// 才保存sessionID
			{
				MongDBUtil.WriteMongodb(sendReport.getSendIP(), session.getRemoteAddress().toString(), sendReport.getSendType(), sendReport.getSendTerminal());
			} else {
				SendIP = MongDBUtil.GetMongodb(sendReport.getSendIP(), sendReport.getSendTerminal());// 通过sendUser
																					// 得到sessionID
			}*/
			switch (Integer.valueOf(sendReport.getSendType())) {
			case 0:
				int flag = 0;
				if (sendReport.getSendHead().equalsIgnoreCase("GPS"))// 存GPS数据
				{
					flag = autoDealUploadData.DealUploadData_GPS(sendReport.getSendContent(), sendReport.getSendIP());
					logger.info("=============写入GPS数据=========="+sendReport.getSendContent());
				} 
				else if (sendReport.getSendHead().equalsIgnoreCase("CAN"))// 存CAN数据
				{
					flag = autoDealUploadData.DealUploadData_CAN(sendReport.getSendContent(), sendReport.getSendIP());
					logger.info("=============写入CAN数据=========="+sendReport.getSendContent());
				}
				if (flag == 1) {
					session.write("succeed");// 服务器返回的数据
				} else {
					session.write("fail");// 服务器返回的数据
				}
				break;
			case 1:
				// 向指定客户端发送数据
				Initialization init = Initialization.getInstance();
				HashMap<String, IoSession> clientMap = init.getClientMap();
				if (clientMap == null || clientMap.size() == 0) {
					session.write("服务器请求失败");
				} else {
					IoSession longConnSession = null;
					longConnSession = clientMap.get(SendIP.replace("\"", ""));
					//logger.info("Short Connect Server Session ID :" + String.valueOf(session.getId()));
					//logger.info("Long Connect Server Session ID :" + String.valueOf(longConnSession.getId()));
					if (sendReport.getSendHead().toString().equalsIgnoreCase("closeWindow"))// 关窗操作
					{
						if (sendReport.getSendContent().equals("0"))// 手机请求关窗操作
						{
							longConnSession.write("{\"sendType\":\"1\",\"result\":\"succeed\"}");// 下发关窗指令
							session.write("{\"sendType\":\"1\",\"result\":\"succeed\"}");// 手机等待完成的指令
						} else if (sendReport.getSendContent().equals("1"))// 通知手机已经完成关窗操作
						{
							longConnSession.write("{\"sendType\":\"1\",\"result\":\"finished\"}");// 通知手机已完成关窗
							session.write("{\"sendType\":\"1\",\"result\":\"finished\"}");// 手机等待完成的指令
						}
					}
				} // String text =
					// URLEncoder.encode("返回:"+sendReport.getSendContent(),
					// "UTF-8");
				break;
			case 2:
				// 拿到所有的客户端Session
				Collection<IoSession> sessions = session.getService().getManagedSessions().values();
				// 向所有客户端发送数据
				for (IoSession sess : sessions) {
					logger.info("发送IP为:" + session.getRemoteAddress().toString());
					sess.write(sendReport.getSendContent());
				}
				session.close();// 关闭客户端的连接
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			session.write("服务器得到数据：发送的数据格式不正确");// 服务器返回的数据
			logger.info("服务器得到数据：发送的数据格式不正确:sessionID-->" + session.getRemoteAddress().toString());
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
/*		Initialization init = Initialization.getInstance();
		HashMap<String, IoSession> clientMap = init.getClientMap();
		if(clientMap!=null)
		{
			clientMap.remove(session.getRemoteAddress().toString());
			super.sessionClosed(session);
			logger.info("客户端与服务端断开连接===================");
			logger.info("在线终端======================: ");
			Iterator iterator = clientMap.keySet().iterator();
			while (iterator.hasNext()) {
				System.out.println(clientMap.get(iterator.next()));
			}
		}*/
		super.sessionClosed(session);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
/*		logger.info("客户端来了,请注意======================: " + session.getRemoteAddress());
		InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
		String clientIp = remoteAddress.getAddress().getHostAddress();

		Initialization init = Initialization.getInstance();
		HashMap<String, IoSession> clientMap = init.getClientMap();
		Iterator iterator = clientMap.keySet().iterator();
		logger.info("在线终端======================: ");
		boolean f = false;
		while (iterator.hasNext()) {
			InetSocketAddress remoteAddress1 = (InetSocketAddress) clientMap.get(iterator.next()).getRemoteAddress();
			if(remoteAddress1!=null)
			{
			String clientIp1 = remoteAddress1.getAddress().getHostAddress();
			if (clientIp1.equals(clientIp)) {
				f = true;
				break;
			}
			}
			else
			{
				clientMap.put(session.getRemoteAddress().toString(), session);
				f = true;
			}
			
		}
		if (f == false) {
			clientMap.put(session.getRemoteAddress().toString(), session);
		}
		*//************* 输出在线连接数 **********//*
		Iterator iteratorT = clientMap.keySet().iterator();
		while (iteratorT.hasNext()) {
			System.out.println(clientMap.get(iteratorT.next()));
		}*/
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		// logger.info("客户端空闲断开连接...");
		// disconnect an idle client
		// 如果IoSession闲置，则关闭连接
		if (status == IdleStatus.BOTH_IDLE) {
			logger.info("客户端空闲断开连接   关闭SessionID====================:" + session.toString());
			session.write("quit");
			session.close(true);
		}
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("-IoSession实例:" + session.toString());
		// 设置IoSession闲置时间，参数单位是秒
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 120);
	}
}
