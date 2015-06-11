package com.rest.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class ConnUtil {

	public static String sendPostZip(String serverUrl, String method,
			String param, String appKey, String signKey) {
	
		String result = "";
		OutputStream os = null;
		InputStream is = null;
		StringBuffer url = new StringBuffer();
		url.append(serverUrl);
		url.append(method);
		try {
			URL realUrl = new URL(url.toString());
			URLConnection conn = realUrl.openConnection();

			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"application/octet-stream;charset=UTF-8");
			conn.setConnectTimeout(1000000);
			conn.setReadTimeout(1000000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			os = conn.getOutputStream();
			StringBuilder sb = new StringBuilder();
            System.out.println("param:"+param);
			System.out.println("sign:"+param.substring(param.indexOf("\"body\":{") + 7, param.length() - 1)+ signKey);
			String sign = MD5.getMD5(param.substring(
					param.indexOf("\"body\":{") + 7, param.length() - 1)
					+ signKey);
			sb.append(param.substring(0, param.indexOf("\"user_id\"")));
			sb.append("\"ver\":\"1.0\",\"sign_m\":\"md5\",\"app_key\":\""
					+ appKey + "\",\"sign\":\"" + sign + "\",");
			sb.append(param.substring(param.indexOf("\"user_id\"")));
			System.out.println("send:\n"+sb);
			byte[] in = ZipUtil.compress(sb.toString().getBytes("UTF-8"));
			os.write(in);
			os.flush();

			int outLen = conn.getContentLength();
			is = conn.getInputStream();
			byte[] utf8Str = ZipUtil.decompress(is, outLen);
			result = new String(utf8Str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	
		return result;
	}

	public static void validRequestParam(String gbkStr, String signKey)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode;
		try {
			jsonNode = mapper.readTree(gbkStr);
			System.out.println("gbkStr:"+gbkStr);
			int bodyIndex = gbkStr.indexOf("\"body\":{");
			if (bodyIndex == -1) {
				throw new Exception("未找到body!");
			}
			System.out.println(gbkStr+"********"+bodyIndex);
			String bodyStr = gbkStr.substring(bodyIndex + 7,
					gbkStr.length() - 1); // 只验证body里两个大括号中的内容，包括大括号。
			String signValid = MD5.getMD5(bodyStr + signKey);
			JsonNode headNode = jsonNode.get("head");
			int errorCode = headNode.get("error_code").asInt();
			if (errorCode == 0) {
				String sign = headNode.get("sign").textValue();
				if (!sign.equals(signValid)) {
					throw new Exception("body数据异常");
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
