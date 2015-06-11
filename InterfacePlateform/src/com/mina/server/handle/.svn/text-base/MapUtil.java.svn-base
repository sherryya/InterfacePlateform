package com.mina.server.handle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import com.db.dto.ZdcGpsinfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
public class MapUtil {
	static Logger logger = Logger.getLogger(MapUtil.class.getName());
	//double lon=12640.850240;
	//double lat=4544.661350;
	public static String getBaiduLocation(String str_lon,String str_lat)
	{
		 double lon=Double.valueOf(str_lon);
		 double lat=Double.valueOf(str_lat);
	     int i_lat=	(int)(lat/100);
	     int i_lon=	(int)(lon/100);
	     long l_lon=  Long.valueOf( String.valueOf(lon/100).replaceAll("\\d+\\.", ""))*100/60;
	     long l_lat=  Long.valueOf( String.valueOf(lat/100).replaceAll("\\d+\\.", ""))*100/60;
	     String s_lon= i_lon+"."+ l_lon;
	     String s_lat= i_lat+"."+l_lat;
	     return s_lon+","+s_lat;
	}
	public static List<ZdcGpsinfo>  getBaidudb09ll(List<ZdcGpsinfo> zdcGpsinfo) throws Exception
	{
		List<ZdcGpsinfo>  gpss=new ArrayList<ZdcGpsinfo>();
		String lonlats="";
		for (ZdcGpsinfo gps : zdcGpsinfo) {
			if (lonlats.length() == 0) {
				lonlats =getBaiduLocation(gps.getLongitude().toString() , gps.getLatitude().toString());
			} else {
				lonlats = lonlats + getBaiduLocation(gps.getLongitude().toString() , gps.getLatitude().toString()) + ";";
			}
		}
		String url="http://api.map.baidu.com/geoconv/v1/?coords=";
		//http://api.map.baidu.com/geoconv/v1/?coords=126.6808373,45.744355833333341&from=1&to=5&ak=flj1sH6FvA3RaxqrwXAVAprf
		System.out.println(lonlats);
		String retStr = "";
		try{
			url=url+lonlats+"&from=1&to=5&ak=flj1sH6FvA3RaxqrwXAVAprf";
			java.net.URL getUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.setRequestProperty("contentType", "utf-8");
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String lines;
			while ((lines = reader.readLine()) != null) {
				retStr = retStr + lines;
			}
			reader.close();
			connection.disconnect();
		}catch(Exception e){
			e.printStackTrace();
		}
		ObjectMapper objectmapper = new ObjectMapper();
		JsonNode jsonNode = objectmapper.readTree(retStr);
		// 1.解析jsonNode
		if (jsonNode != null) {
			int status = jsonNode.get("status").asInt();
			if (status == 0) {
				JsonNode json_data = jsonNode.get("result");
				Iterator it = json_data.iterator();
				while (it.hasNext()) {
					JsonNode json_sub_content = (JsonNode) it.next();
					System.out.println(json_sub_content.get("x"));
					System.out.println(json_sub_content.get("y"));
					
					for (ZdcGpsinfo gps : zdcGpsinfo) 
					{
						gps.setLongitude(Double.valueOf(json_sub_content.get("x").toString()));
						gps.setLatitude(Double.valueOf(json_sub_content.get("y").toString()));
						
						gpss.add(gps);
					}
				}
			}
		}
		return gpss;
	}
	public static void main(String[] args) throws IOException, DocumentException {
		String ret=getBaiduLocation("12640.85024" , "4544.66135");
		String url="http://api.map.baidu.com/geoconv/v1/?coords=";
		//http://api.map.baidu.com/geoconv/v1/?coords=126.6808373,45.744355833333341&from=1&to=5&ak=flj1sH6FvA3RaxqrwXAVAprf
		System.out.println(ret);
		String retStr = "";
		try{
			url=url+ret+"&from=1&to=5&ak=flj1sH6FvA3RaxqrwXAVAprf";
			java.net.URL getUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.setRequestProperty("contentType", "utf-8");
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String lines;
			while ((lines = reader.readLine()) != null) {
				retStr = retStr + lines;
			}
			reader.close();
			connection.disconnect();
		}catch(Exception e){
			e.printStackTrace();
		}
		ObjectMapper objectmapper = new ObjectMapper();
		JsonNode jsonNode = objectmapper.readTree(retStr);
		// 1.解析jsonNode
		if (jsonNode != null) {
			int status = jsonNode.get("status").asInt();
			if (status == 0) {
				JsonNode json_data = jsonNode.get("result");
				Iterator it = json_data.iterator();
				while (it.hasNext()) {
					JsonNode json_sub_content = (JsonNode) it.next();
					System.out.println(json_sub_content.get("x"));
					System.out.println(json_sub_content.get("y"));
				}
			}
		}
	}
}
