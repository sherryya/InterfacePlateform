package com.crontab.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;


public class BreakPlatUtil {
	
	/**
	 * 20141128
	 * @param engineno 发动机号
	 * @param classno  车架号
	 * @param hphm 号牌号码
	 * @return
	 */
	private static String breakrules_citylist_url1="http://v.juhe.cn/wz/query?city=";
	private static String breakrules_key1= "35d4681985d16ac0657233a7aab76fe5";
	public static String getInfo(String city_code,String engineno,String classno,String hphm)
	{
		String url=breakrules_citylist_url1+city_code+"&key="+breakrules_key1;
		if(StringUtils.isNotBlank(engineno))
		{
			engineno = "&engineno="+engineno;
		}
		if(StringUtils.isNotBlank(classno))
		{
			classno = "&classno="+classno;
		}
		if(StringUtils.isNotBlank(hphm))
		{
			try {
				hphm = URLEncoder.encode(hphm, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hphm = "&hphm="+hphm;
		}
		url = url+hphm+engineno+classno;
		String charset = "UTF-8";
		String json = get(url, charset);
		System.out.println("breakrules_citylist_response_json" + json);
		return json;
	}
	
	public static String get(String urlAll, String charset) {
		BufferedReader reader = null;
		String result = "";
		StringBuffer sbf = new StringBuffer();
		String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";// 模拟浏览器
		try {
			URL url = new URL(urlAll);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			
			connection.setRequestProperty("User-agent", userAgent);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, charset));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 20141128
	 * 根据省代码，获取支持城市参数接口 
	 * province string N 默认全部，省份简写，如：ZJ、JS dtype string N
	 * 返回数据格式：json或xml或jsonp,默认json callback String N 返回格式选择jsonp时，必须传递 key
	 * string Y 你申请的key
	 */
	public static String getAllCityList() {
		String url = "http://v.juhe.cn/wz/citys?key=35d4681985d16ac0657233a7aab76fe5&dtype=json&province=&format=";
		System.out.println("breakrules_allcitylist_url" + url);
		String charset = "UTF-8";
		String json = get(url, charset);
		System.out.println("breakrules_allcitylist_response_json" + json);
		return json;
	}

}
