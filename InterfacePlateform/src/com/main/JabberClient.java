package com.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;

import com.dto.SendReport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JabberClient {
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			//客户端socket指定服务器的地址和端口号
			socket = new Socket("127.0.0.1", 8005);
			SendReport sendReport = new SendReport();
			sendReport.setSendContent("我发送测试111111");
			sendReport.setSendIP("liang4");
			sendReport.setSendType("1");
			Gson gson = new Gson();
			Type listType = new TypeToken<SendReport>() {
			}.getType();
			String json = gson.toJson(sendReport, listType);
			
			System.out.println("Socket=" + socket);
			//同服务器原理一样
			br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())));
		/*	for (int i = 0; i < 10; i++) {
				pw.println("howdy " + i);
				pw.flush();
				String str = br.readLine();
				System.out.println(str);
			}*/
			pw.println(json);
			pw.flush();
			String str = br.readLine();
			System.out.println("==========="+str);
			//pw.println("END");
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("close......");
				br.close();
				pw.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
