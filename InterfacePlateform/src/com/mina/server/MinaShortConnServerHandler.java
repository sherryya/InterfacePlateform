package com.mina.server;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dto.SendReport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class MinaShortConnServerHandler extends IoHandlerAdapter {
	private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());

	@Override
	public void sessionOpened(IoSession session) {
		InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
		logger.info(remoteAddress.getAddress().getHostAddress());
		logger.info(String.valueOf(session.getId()));
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		logger.info("Message received in the short connect server...");
		String expression = message.toString();
		logger.info("接收内容=================================："+expression);
		SendReport sendReport = new SendReport();
		//try {
			Type listType = new TypeToken<SendReport>() {}.getType();
			Gson gson = new Gson();
			sendReport = gson.fromJson(expression, listType);
			System.out.println(sendReport.getSendContent());
			System.out.println(sendReport.getSendIP());
			System.out.println(sendReport.getSendType());
			Date date = new Date();
			String SendIP="";
			logger.info((" 短连接   发送内容："+expression+"；发送对象："+session.getRemoteAddress()+"；发送时间:"+date.toString()));
			//if(sendReport.getSendType().equalsIgnoreCase("0"))//只有终端发送的时候 才保存sessionID
			 try {
				SendIP=MongDBUtil.GetMongodb(sendReport.getSendIP(),sendReport.getSendTerminal());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//通过sendUser 得到sessionID 
		Initialization init = Initialization.getInstance();
		HashMap<String, IoSession> clientMap = init.getClientMap();
		if (clientMap == null || clientMap.size() == 0) {
			session.write("error");
		} else {
			IoSession longConnSession = null;
			longConnSession = clientMap.get(SendIP.replace("\"", ""));
			logger.info("Short Connect Server Session ID :"+String.valueOf(session.getId()));
			logger.info("Long Connect Server Session ID :"+String.valueOf(longConnSession.getId()));
			longConnSession.setAttribute("shortConnSession",session);
			longConnSession.write("短连接："+expression);
			//session.write("我收到信息了:");
			//session.close();
		}
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		//logger.info("客户端空闲断开连接...");
		//disconnect an idle client
		// 如果IoSession闲置，则关闭连接
	   if (status == IdleStatus.BOTH_IDLE)
	   {
	    logger.info(" 短连接    客户端空闲断开连接   关闭SessionID====================:" + session.toString());
		//session.close(true);
	   }
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		// close the connection on exceptional situation
		logger.warn(cause.getMessage(), cause);
		session.close(true);
	}
/*	@Override
	public void sessionClosed(IoSession session) throws Exception {
		
		super.sessionClosed(session);
		logger.info("客户端与服务端断开连接===================");
		logger.info("在线终端  短连接======================: ");
		
	}*/
	
}
