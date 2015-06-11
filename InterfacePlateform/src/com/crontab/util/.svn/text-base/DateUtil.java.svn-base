package com.crontab.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 * 日期工具类
 * 
 * @author hexin
 */
public class DateUtil {

	private final static String DATE_DAY = "yyyy-MM-dd";
	private final static String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	private final static Map<String, String> MonthDate = new HashMap<String, String>();
	static{
		MonthDate.put("Jan", "01");
		MonthDate.put("Feb", "02");
		MonthDate.put("Mar", "03");
		MonthDate.put("Apr", "04");
		MonthDate.put("May", "05");
		MonthDate.put("Jan", "06");
		MonthDate.put("Jul", "07");
		MonthDate.put("Aug", "08");
		MonthDate.put("Sep", "09");
		MonthDate.put("Oct", "10");
		MonthDate.put("Nov", "11");
		MonthDate.put("Dec", "12");
		
	}

	/**
	 * 与当前日期比较
	 * 
	 * @param timeStr
	 *            日期字符串[格式:yyyy-MM-dd]
	 * @return 与当前日期是否不一致
	 */
	public boolean dateCompare(String timeStr) {
		return new SimpleDateFormat(DATE_DAY).format(new Date()).split(" ")[0].equals(timeStr.split(" ")[0]);
	}

	/**
	 * 与当前日期比较
	 * 
	 * @param timeStr
	 *            日期字符串[格式:请指定类型]
	 * @return 与当前日期是否不一致
	 */

	public boolean dateCompare(String timeStr, String type) {
		return new SimpleDateFormat(type).format(new Date()).split(" ")[0].equals(timeStr.split(" ")[0]);
	}

	/**
	 * 与指定日期类型的比较
	 * @param timeStrA 日期字符串A
	 * @param timeStrB 日期字符串B
	 * @param type 日期格式
	 * @return 返回 0 表示时间日期相同 返回 1 表示日期1>日期2 返回 -1 表示日期1<日期2
	 * @throws ParseException 解析异常
	 */
	public boolean dateCompare(String timeStrA, String timeStrB, String type) throws ParseException {
		 return new SimpleDateFormat(type).parse(timeStrA).compareTo(new SimpleDateFormat(type).parse(timeStrB)) == 1;
	}

	/**
	 * 获取当前日期字符串
	 * 
	 * @param type 定义日期格式： [格式:yyyy-MM-dd HH:mm:ss] [格式:yyyy-MM-dd]
	 * @return 日期字符串
	 */
	public String gainDate(String type) {
		return new SimpleDateFormat(type).format(new Date());
	}

	/**
	 * 格林尼治GMT时间转换成北京时间
	 * @param pubDate 格林尼治时间(GMT:格林尼治时间)
	 * @return
	 */
	public String dateConvert(String pubDate) {
		String result = null;
		String[] timeParts = pubDate.substring(pubDate.indexOf(",") + 1).replace("GMT", "").trim().split(" ");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d = sd.parse(timeParts[2] + "-" + MonthDate.get(timeParts[1])+ "-" + timeParts[0] + " " + timeParts[3]);
			Calendar cal  = Calendar.getInstance();
			cal.setTimeInMillis(d.getTime());
			cal.add(Calendar.HOUR, +8);
			result = sd.format(cal.getTime());
		} catch (ParseException e) {
			result = gainDate("yyyy-MM-dd HH:mm:ss");
		}
		return result;
	}
	
	public static String getFileName() {
		long time = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		Date d1 = new Date(time);
		String fileName = format.format(d1);
		return fileName;
	}
	
	public static String getDateyyyyMMDD() {
		long time = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date d1 = new Date(time);
		String fileName = format.format(d1);
		return fileName;
	}
	public static void main(String[] args) {
		System.out.println(getDateyyyyMMDD());
	}
}
