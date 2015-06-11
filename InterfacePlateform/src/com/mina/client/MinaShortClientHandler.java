package com.mina.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaShortClientHandler extends IoHandlerAdapter{
	private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());

	public MinaShortClientHandler() {
		
	}

	@Override
	public void sessionOpened(IoSession session) {
		
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		logger.info("Message received in the client..");
		logger.info("Message is: " + message.toString());
		session.setAttribute("result", message.toString());
		//session.close(true);
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("短连接     客户端与服务端断开连接.....");
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
	public void sessionCreated(IoSession session) throws Exception {
	
		System.out.println("one Client Connection" + session.getRemoteAddress());
		//session.write("短连接   我来了······");
		SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();  
        cfg.setReceiveBufferSize(2 * 1024 * 1024);  
        cfg.setReadBufferSize(2 * 1024 * 1024);  
        cfg.setKeepAlive(true);  
        cfg.setSoLinger(0); //这个是根本解决问题的设置  
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		session.close(true);
	}
}
