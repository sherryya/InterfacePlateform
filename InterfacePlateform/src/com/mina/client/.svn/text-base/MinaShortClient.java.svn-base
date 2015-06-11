package com.mina.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.dto.SendReport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MinaShortClient {
	private static final int PORT = 8006;

	public static void main(String[] args) throws IOException, InterruptedException {
		IoConnector connector = new NioSocketConnector();
		connector.getSessionConfig().setReadBufferSize(2048);

		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

		connector.setHandler(new MinaShortClientHandler());
		//for (int i = 1; i <= 10; i++) {
			ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", PORT));
			//ConnectFuture future = connector.connect(new InetSocketAddress("222.171.242.178", 18001));
			future.awaitUninterruptibly();
			
		    SendReport sendReport=new SendReport();
	        sendReport.setSendContent("我发送测试55555");
	        sendReport.setSendIP("liang4");
	        sendReport.setSendType("3");
	        Gson gson=new Gson();
	        Type listType = new TypeToken<SendReport>(){}.getType();
	        String json=gson.toJson(sendReport, listType);
		        
			IoSession session = future.getSession();
			session.write(json);
			session.getCloseFuture().awaitUninterruptibly();
			System.out.println("result00000 =" + session.getAttribute("result"));
		//}
		connector.dispose();

	}
}
