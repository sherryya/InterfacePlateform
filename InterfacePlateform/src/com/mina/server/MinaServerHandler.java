package com.mina.server;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dto.SendReport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mina.server.handle.CanStreamHandle;
/**
 * 继承自IoHandlerAdapter，IoHandlerAdapter继承接口 IoHandler
 * 类IoHandlerAdapter实现了IoHandler的所有方法，只要重载关心的几个方法就可以了
 */
public class MinaServerHandler extends IoHandlerAdapter {
	private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
	AutoDealUploadData autoDealUploadData = new AutoDealUploadData();
	CanStreamHandle canStreamHandle=new CanStreamHandle();
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
		if(str.trim().length()==0)
		{
			session.close();
			return;
		}
		logger.info("接收内容=================================：" + str);
	
		if (str.trim().equalsIgnoreCase("quit")) {
			session.close();
			return;
		}
		if (str.trim().equalsIgnoreCase("1")) {
			logger.info("收到心跳:"+session.getRemoteAddress());
			return;
		}
		SendReport sendReport = new SendReport();
		try {
			Type listType = new TypeToken<SendReport>() {
			}.getType();
			Gson gson = new Gson();
			sendReport = gson.fromJson(str, listType);
			System.out.println(sendReport.getSendContent()+","+sendReport.getSendIP()+","+sendReport.getSendType()+","+sendReport.getSendHead()+","+sendReport.getSendTerminal());
			Date date = new Date();
			logger.info(("发送内容：" + str + "；发送对象：" + session.getRemoteAddress() + "；发送时间:" + date.toString()));
			
			Initialization init = Initialization.getInstance();
			HashMap<String, IoSession> clientMap = init.getClientMap();
			IoSession longConnSession = null;
			
			if (sendReport.getSendHead().equalsIgnoreCase("CAN")) {
				String session_key = sendReport.getSendIP();// 设置保存Session的标记
				if (sendReport.getSendType().equalsIgnoreCase("3"))// 只有客服端发送的时候 才保存sessionID  维护后台的长连接
				{
					if (clientMap.containsKey(session_key))// 判断缓存内是否有sessionID  如果存在，那么先移除，再保存最新的,防止session过期
					{
						longConnSession = clientMap.get(session_key);// 得到sesionID
						if (!longConnSession.equals(session))// 如果session值不同才处理
						{
							longConnSession.close();
							clientMap.remove(session_key);
							clientMap.put(session_key, session);// 保存SessionID
						}
					} else {
						clientMap.put(session_key, session);// 保存SessionID
					}
					// ************* 输出在线连接数 调试程序**********//*
					logger.info("在线终端======================: ");
					@SuppressWarnings("rawtypes")
					Iterator iteratorT = clientMap.keySet().iterator();
					while (iteratorT.hasNext()) {
						System.out.println(clientMap.get(iteratorT.next()));
					}
					clientMap.get(session_key).write("longSocket succeed");
					String str1 = clientMap.get(session_key).getRemoteAddress().toString();
					logger.info("在线终端  服务器IP======================: "+str1);
					System.out.println(str1);
				} else if (sendReport.getSendType().equalsIgnoreCase("1")) {
					longConnSession = clientMap.get(session_key);// 得到sesionID
				}
			}
			
			switch (Integer.valueOf(sendReport.getSendType())) {
			case 0://客服端上传数据
				int flag = 0;
				if (sendReport.getSendHead().equalsIgnoreCase("GPS"))// 存GPS数据
				{
					flag = autoDealUploadData.DealUploadData_GPS(sendReport.getSendContent(), sendReport.getSendIP());
					logger.info("=============写入GPS数据=========="+sendReport.getSendContent());
				} 
				else if (sendReport.getSendHead().equalsIgnoreCase("CAN"))// 存CAN数据
				{
					String streamCan=sendReport.getSendContent().toString();
					streamCan=streamCan.substring(streamCan.toUpperCase().indexOf("2E"), streamCan.length());
					flag = canStreamHandle.handleCanStream(streamCan, sendReport.getSendIP().toString());
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
				if (clientMap == null || clientMap.size() == 0) {
					session.write("fail");
				} else {
					if(longConnSession!=null)
					{
						Collection<IoSession> sessions = session.getService().getManagedSessions().values();//得到所有有效的session值
						if(sessions.contains(longConnSession))
						{
							logger.info("=============发送到终端=========="+sendReport.getSendContent().toString()+" session:  "+longConnSession);
							longConnSession.write(sendReport.getSendContent().toString());
						}
						else
						{
							logger.info("=============session====="+longConnSession+" 已失效=====");
						}
					}
					else
					{
						logger.info("============SessionID不存在=========="+sendReport.getSendContent().toString());
					}
				}
				
				session.close();
				break;
/*			case 2:
				// 拿到所有的客户端Session
				Collection<IoSession> sessions = session.getService().getManagedSessions().values();
				// 向所有客户端发送数据
				for (IoSession sess : sessions) {
					logger.info("发送IP为:" + session.getRemoteAddress().toString());
					sess.write(sendReport.getSendContent());
				}
				session.close();// 关闭客户端的连接
				break;*/
			}
		} catch (Exception e) {
			// TODO: handle exception
			session.write("fail");// 服务器返回的数据
			logger.info("服务器得到数据：发送的数据格式不正确,或sessionID　不存在:sessionID-->" + session.getRemoteAddress().toString()+":异常:"+e.getMessage());
		}
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		Initialization init = Initialization.getInstance();
		HashMap<String, IoSession> clientMap = init.getClientMap();
		try {
			if (clientMap != null) {
				logger.info("客户端与服务端断开连接===================");
				logger.info("在线终端======================: ");
				// Collection<IoSession> sessions =
				// session.getService().getManagedSessions().values();//得到所有有效的session值
				/*
				 * Iterator iterator = clientMap.keySet().iterator(); while
				 * (iterator.hasNext()) {
				 * System.out.println(clientMap.get(iterator.next())); if
				 * (!sessions.contains(clientMap.get(iterator.next()))) {
				 * 
				 * } }
				 */
				Set<Entry<String, IoSession>> set = clientMap.entrySet();
				Iterator<Entry<String, IoSession>> i = set.iterator();
				while (i.hasNext()) {
					@SuppressWarnings("rawtypes")
					Map.Entry me = (Map.Entry) i.next();
					logger.info("在线终端====key:" + me.getKey() + "=========value:" + me.getValue() + "=========RemoteAddress: " + ((IoSession) me.getValue()).getRemoteAddress() + "=========LocalAddress: " + ((IoSession) me.getValue()).getLocalAddress());
					if (((IoSession) me.getValue()).getLocalAddress().equals("0.0.0.0/0.0.0.0:8004")) { 
					  clientMap.remove(me.getKey()); } 
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("客户端来了,请注意======================: " + session.getRemoteAddress());
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
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 200);
	}
}
