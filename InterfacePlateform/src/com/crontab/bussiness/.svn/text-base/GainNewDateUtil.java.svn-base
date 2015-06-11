package com.crontab.bussiness;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GainNewDateUtil {
    // 在类实例化的时候创建静态的时间字符串
    private static Map<String, String> MonthDate = new HashMap<String, String>();
    static {
	MonthDate.put("Jan", "01");
	MonthDate.put("Feb", "02");
	MonthDate.put("Mar", "03");
	MonthDate.put("Apr", "04");
	MonthDate.put("May", "05");
	MonthDate.put("Jun", "06");
	MonthDate.put("Jul", "07");
	MonthDate.put("Aug", "08");
	MonthDate.put("Sep", "09");
	MonthDate.put("Oct", "10");
	MonthDate.put("Nov", "11");
	MonthDate.put("Dec", "12");
    }

    public static boolean compare_date(String DATE1, String DATE2) {
	boolean isBig=false;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	try {
	    if (df.parse(DATE1).getTime() > df.parse(DATE2).getTime()) {
		isBig=true;
	    }
	} catch (Exception exception) {
	    
	}
	return isBig;
    }

    // 获取北京时间
    public static String DateUtil(String pubDate) {
	String result = null;
	String[] timeParts = pubDate.substring(pubDate.indexOf(",") + 1)
		.replace("GMT", "").trim().split(" ");
	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	try {
	    Date d = sd.parse(timeParts[2] + "-" + MonthDate.get(timeParts[1])
		    + "-" + timeParts[0] + " " + timeParts[3]);
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(d.getTime());
	    cal.add(Calendar.HOUR, +8);
	    result = sd.format(cal.getTime());
	} catch (ParseException e) {
	    result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") .format(new Date());
	}
	return result;
    }

    public static boolean ifDate(String dateUtil) {
	boolean isNeeds = false;
	return isNeeds;
    }

    public static boolean daysBetween(String smdate) throws ParseException {
	boolean isNeeds = true;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();
	cal.setTime(sdf.parse(smdate));
	long time1 = cal.getTimeInMillis();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	cal.setTime(new Date());
	long time2 = cal.getTimeInMillis();
	long between_days = (time2 - time1) / (1000 * 3600 * 24);
	if (Integer.parseInt(String.valueOf(between_days)) > 2) {
	    isNeeds = false;
	}
	return isNeeds;
    }
}
