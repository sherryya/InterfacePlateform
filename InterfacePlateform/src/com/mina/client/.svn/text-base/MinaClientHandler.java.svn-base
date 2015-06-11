package com.mina.client;
import java.net.URLDecoder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 客户端业务处理逻辑
 * 
 */
public class MinaClientHandler extends IoHandlerAdapter {
	String  sendReport="";
	private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
	public MinaClientHandler()
	{
	}
	public MinaClientHandler(String sendReport)
	{
		this.sendReport=sendReport;
	}
	//"{\"sendType\":\"1\",\"sendIP\":\"192.168.0.104\",\"sendContent\":\"你好吗\"}"
	// 当客户端连接进入时
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("incomming 客户端: " + session.getRemoteAddress());
		session.write(this.sendReport);
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("客户端发送信息异常....");
	}
	// 当客户端发送消息到达时
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println("服务器返回的数据11：" + URLDecoder.decode(message.toString(),"UTF-8"));// message.toString());

	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("客户端与服务端断开连接....."); 
	}
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("one Client Connection" + session.getRemoteAddress());
		//session.write("我来了······");
		SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();  
        cfg.setReceiveBufferSize(2 * 1024 * 1024);  
        cfg.setReadBufferSize(2 * 1024 * 1024);  
        cfg.setKeepAlive(true);  
        cfg.setSoLinger(0); //这个是根本解决问题的设置  
	}
}
