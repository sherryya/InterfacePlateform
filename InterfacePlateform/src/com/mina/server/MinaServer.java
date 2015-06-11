package com.mina.server;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.main.Sev;
public class MinaServer {
	private static final Logger log = Logger.getLogger(Sev.class.getName());
	// 服务器监听端口
	//private static final int PORT = 10015;
	private static final int PORT = 8088;
	/**
	 * 
	 */
	public MinaServer() {
		// TODO Auto-generated constructor stub
	}
	private static final int BUFFER_SIZE = 20 * 1024;
	private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
		public Thread newThread(final Runnable r) {
			return new Thread(null, r, "MinaThread", 20 * 1024);
		}
	};
	private OrderedThreadPoolExecutor executor;
	public  void start()
	{
		/*executor = new OrderedThreadPoolExecutor(0, 1000, 60, TimeUnit.SECONDS,	THREAD_FACTORY);
		SocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setReceiveBufferSize(BUFFER_SIZE);
        // 设置Filter链 
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        // 协议解析，采用mina现成的UTF-8字符串处理方式
		TextLineCodecFactory lineCodec=new TextLineCodecFactory(Charset.forName("UTF-8")); 
		lineCodec.setDecoderMaxLineLength(1024*1024); //1M  
 		lineCodec.setEncoderMaxLineLength(1024*1024); //1M  
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(lineCodec));
		//acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
		acceptor.getFilterChain().addLast("threadPool",new ExecutorFilter(executor));
		// 设置消息处理类（创建、关闭Session，可读可写等等，继承自接口IoHandler）
		acceptor.setHandler(new MinaServerHandler() );
        // 设置接收缓存区大小
		acceptor.getSessionConfig().setReadBufferSize(10*2048);
		//acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		try {
            // 服务器开始监听
			acceptor.bind( new InetSocketAddress(PORT) );
			log.info(" =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= starts 长连接   监听端口 :"+PORT+"=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ");
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
		
		IoAcceptor acceptor = new NioSocketAcceptor();
 		// 设置Filter链
 		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
 		// 协议解析，采用mina现成的UTF-8字符串处理方式
 		TextLineCodecFactory lineCodec=new TextLineCodecFactory(Charset.forName("UTF-8"),"#","#"); //以 # 号作为结束服务
 		lineCodec.setDecoderMaxLineLength(1024*1024); //1M  
 		lineCodec.setEncoderMaxLineLength(1024*1024); //1M  

 		acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(lineCodec));
 		// 设置消息处理类（创建、关闭Session，可读可写等等，继承自接口IoHandler）
 		
 		
 		
 		acceptor.setHandler(new MinaServerHandler());
 		// 设置接收缓存区大小
 		acceptor.getSessionConfig().setReadBufferSize(4048);
 		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 200);
 		try {
 			// 服务器开始监听
 			acceptor.bind(new InetSocketAddress(PORT));
 			log.info(" =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= starts 长连接  GPS  监听端口 :"+PORT+"=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
	}
	
	
		//acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
}