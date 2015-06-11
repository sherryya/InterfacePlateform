package com.main;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import com.crontab.AlarmOilLow;
import com.crontab.BreakRulesSmsPush;
import com.crontab.CarBreakRulesStream;
import com.crontab.CronAutoClacCanStream;
import com.crontab.CronAutoClacMileage;
import com.crontab.CronAutoClacMileageNew;
import com.crontab.CronAutoNewsGainer;
import com.crontab.CronAutoNewsTotalGainer;
import com.crontab.CronAutoNewsTruncate;
import com.crontab.QuartzGpsOrignal;
import com.mina.server.Initialization;
import com.mina.server.MinaServer;
public class Sev extends HttpServlet {
	private static final Logger log = Logger.getLogger(Sev.class.getName());
	// 服务器监听端口
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {	
		super.doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		super.doPost(req, resp);
	}
	@Override  
    public void init() throws ServletException {  
        // TODO Auto-generated method stub  
        super.init();  
     // 服务器端的主要对象
    	//   try {
			try {
				Initialization.getInstance().init();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//new MinaServer().start();
			RunCronTab();
	//	} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block   
		//	e1.printStackTrace();
	//	}
    }	
	private void RunCronTab()
	{
		CronAutoClacCanStream cronAutoClacCanStream=new CronAutoClacCanStream();
		//CronAutoClacMileage cronAutoClacMileage=new CronAutoClacMileage();
		QuartzGpsOrignal quartzGpsOrignal=new QuartzGpsOrignal();
		CronAutoClacMileageNew cronAutoClacMileage=new CronAutoClacMileageNew();
		CronAutoNewsGainer cronAutoNewsGainer = new CronAutoNewsGainer();
		CronAutoNewsTotalGainer cronAutoNewsTotalGainer=new CronAutoNewsTotalGainer();
		CronAutoNewsTruncate cronAutoNewsTruncate=new CronAutoNewsTruncate();
		CarBreakRulesStream carBreakRulesStream = new CarBreakRulesStream();
		BreakRulesSmsPush breakRulesPush = new BreakRulesSmsPush();  //违章信息的定时任务
		AlarmOilLow aoil = new AlarmOilLow();
		 try {
			QuartzManager.addJob("cronAutoClacCanStream",cronAutoClacCanStream,"0 0/5 * * * ?");
			QuartzManager.addJob("cronAutoClacMileage", cronAutoClacMileage, "0 0/8 * * * ?");
		    QuartzManager.addJob("quartzGpsOrignal", quartzGpsOrignal, "0 0/2 * * * ?");
			//QuartzManager.addJob("cronAutoNewsGainer", cronAutoNewsGainer, "0 0/10 * * * ?");
			//QuartzManager.addJob("cronAutoNewsTotalGainer", cronAutoNewsTotalGainer, "0 0/30 * * * ?");
			QuartzManager.addJob("carBreakRulesStream", carBreakRulesStream, "0 0 2 * * ?");  //每天上午两点执行
			//QuartzManager.addJob("cronAutoNewsTruncate", cronAutoNewsTruncate, "0 0 12 * * ?");
			QuartzManager.addJob("breakRulesPush", breakRulesPush, "0 0 0/1 * * ?");
			//QuartzManager.addJob("aoil", aoil, "0 0/1 * * * ?");
		 } catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //每1分钟钟执行一次  
	}	
	private void longSocket()
	{
  		/*IoAcceptor acceptor = new NioSocketAcceptor();
 		// 设置Filter链
 		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
 		// 协议解析，采用mina现成的UTF-8字符串处理方式
 		acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
 		// 设置消息处理类（创建、关闭Session，可读可写等等，继承自接口IoHandler）
 		acceptor.setHandler(new MinaServerHandler());
 		// 设置接收缓存区大小
 		acceptor.getSessionConfig().setReadBufferSize(2048);
 		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
 		try {
 			// 服务器开始监听
 			acceptor.bind(new InetSocketAddress(PORTL));
 			log.info(" =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= starts 长连接   监听端口 :"+PORTL+"=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}*/
	/*	executor = new OrderedThreadPoolExecutor(0, 1000, 60, TimeUnit.SECONDS,	THREAD_FACTORY);
		SocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setReceiveBufferSize(BUFFER_SIZE);
        // 设置Filter链 
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        // 协议解析，采用mina现成的UTF-8字符串处理方式
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		acceptor.getFilterChain().addLast("threadPool",new ExecutorFilter(executor));
		// 设置消息处理类（创建、关闭Session，可读可写等等，继承自接口IoHandler）
		acceptor.setHandler(new MinaServerHandler() );
        // 设置接收缓存区大小
		acceptor.getSessionConfig().setReadBufferSize(4*2048);
		//acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		try {
            // 服务器开始监听
			acceptor.bind( new InetSocketAddress(PORTL) );
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}
}
