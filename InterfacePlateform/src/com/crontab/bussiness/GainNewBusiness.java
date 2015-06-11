package com.crontab.bussiness;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.db.dto.ZdcNewsInfo;
import com.db.service.ZdcNewsTotalUpdateService;
import com.db.service.ZdcNewsUpdateService;
import com.system.SpringApplicationContextFactory;

public class GainNewBusiness {
    private final static String TENCENTURL = "http://rss.qq.com/news.htm";
    private final static String NETEASYURL = "http://www.163.com/rss";
    private final static String SINAURL = "http://rss.sina.com.cn/index.shtml";
    private int type=0;
 
    private static ZdcNewsTotalUpdateService zdcNewsTotalUpdateService = SpringApplicationContextFactory.newInstance().getBean(ZdcNewsTotalUpdateService.class);
    private static ZdcNewsUpdateService zdcNewsUpdateService = SpringApplicationContextFactory.newInstance().getBean(ZdcNewsUpdateService.class);

    public GainNewBusiness(int type) {
	this.type=type;
	try {
	    switch (type) {
	    case 0:
		gainTotalNews(XMLTOTALADDRESS);
		break;
	    case 1:
		gainTotalNews(XMLFOCUSADDRESS);
		break;
	    }
	} catch (DocumentException e) {
	    System.out.println(e.getMessage());
	} catch (ParseException e) {
	    System.out.println(e.getMessage());
	}
    }
 

    // 新闻分类
    private final static String[] CATEGORY = { "新闻中心", "体育频道", "科技频道", "财经频道",
	    "影音娱乐", "军事频道", "汽车新闻", "教育频道" };
    // 全部新闻地址
    private final static String[] XMLTOTALADDRESS = {
	    "http://rss.sina.com.cn/news/marquee/ddt.xml#http://rss.sina.com.cn/news/world/focus15.xml#http://rss.sina.com.cn/news/china/focus15.xml",
	    "http://rss.sina.com.cn/roll/sports/hot_roll.xml#http://rss.sina.com.cn/news/allnews/sports.xml#http://rss.sina.com.cn/sports/global/focus.xml",
	    "http://rss.sina.com.cn/tech/rollnews.xml#http://rss.sina.com.cn/news/allnews/tech.xml#http://rss.sina.com.cn/tech/internet/home28.xml#http://rss.sina.com.cn/tech/it/gn37.xml",
	    "http://rss.sina.com.cn/roll/finance/hot_roll.xml#http://rss.sina.com.cn/news/allnews/finance.xml#http://rss.sina.com.cn/roll/stock/hot_roll.xml",
	    "http://rss.sina.com.cn/ent/hot_roll.xml#http://rss.sina.com.cn/news/allnews/ent.xml",
	    "http://rss.sina.com.cn/roll/mil/hot_roll.xml#http://rss.sina.com.cn/jczs/focus.xml#http://rss.sina.com.cn/jczs/taiwan20.xml",
	    "http://rss.sina.com.cn/news/allnews/auto.xml#http://rss.sina.com.cn/auto/news/t/index.xml#http://rss.sina.com.cn/auto/news/t/index.xml",
	    "http://rss.sina.com.cn/roll/edu/hot_roll.xml#http://rss.sina.com.cn/edu/focus19.xml" };
    
    private final static String[] XMLFOCUSADDRESS = {
	    "http://rss.sina.com.cn/news/world/focus15.xml",
	    "http://rss.sina.com.cn/news/allnews/sports.xml",
	    "http://rss.sina.com.cn/news/allnews/tech.xml",
	    "http://rss.sina.com.cn/news/allnews/finance.xml",
	    "http://rss.sina.com.cn/news/allnews/ent.xml",
	    "http://rss.sina.com.cn/jczs/focus.xml",
	    "http://rss.sina.com.cn/auto/news/t/index.xml",
	    "http://rss.sina.com.cn/edu/focus19.xml" };

    // 0/1
    
    
    
    /**
     * 获取新闻分类中的要点新闻
     * 
     * @throws DocumentException
     * @throws ParseException 
     */
    private void gainTotalNews(String[] newAddress) throws DocumentException, ParseException {
	// 新浪新闻分为滚动新闻与全部新闻
	long time = System.currentTimeMillis();
	List<String> times=null;
	// 将该部分放于实时时间服务中处理，当一天的最后时间执行 zdcNewsTotalUpdateService.clearDataByTime();
	if (type == 0) {
	    times = zdcNewsTotalUpdateService.selectEndTimeSub();
	} else {
	    times = zdcNewsUpdateService.selectEndTimeSub();
	}
	String lastTime = times.size()==0?"NO":times.get(0);
	Set<List<ZdcNewsInfo>> set = new HashSet<List<ZdcNewsInfo>>();
	for (int index = 0; index < newAddress.length; index++) {
	    String url = newAddress[index];
	    List<ZdcNewsInfo> beanlist = new ArrayList<ZdcNewsInfo>();
	    if (url.contains("#")) {
		for (String urls : url.split("#")) {
		   packTime(lastTime, urls, beanlist, index);
		}
	    } else {
		packTime(lastTime, url, beanlist, index);
	    }
	    set.add(beanlist);
	}
	System.out.println("开始获取全部新闻地址并执行逻辑封装--(网络)执行时间:" + (System.currentTimeMillis() - time) / 1000 + "s");
	insertTime(set);
	System.out.println("插入数据"+set.size()+"完成--(本地数据库)执行时间:" + (System.currentTimeMillis() - time) / 1000 + "s");
    }
    
    private void packTime(String lastTime,String url,List<ZdcNewsInfo> beanlist,int index) throws DocumentException, ParseException
    {
	  Iterator<Element> nodeIterator = new SAXReader().read(url).getRootElement().element("channel").elements("item").iterator();
	    while (nodeIterator.hasNext()) {
		Element iteme = (Element) nodeIterator.next();
		String urlName = iteme.element("link").getTextTrim();
		String linkType = urlName.substring(urlName
			.lastIndexOf(".") + 1);
		if (linkType.equals("shtml") || index == 5) {
		    
		    String description=iteme.element("description") .getTextTrim();
		    String title=iteme.element("title").getTextTrim();
		    String pubDate=iteme.element("pubDate").getTextTrim();
		    String pubDateStr=GainNewDateUtil.DateUtil(pubDate);
		    boolean isdescription=!description.equals("");
		    boolean islastTime=!lastTime.equals("NO");
		    boolean islastNow=GainNewDateUtil.daysBetween(pubDateStr);
		    
		if (islastTime) {
		    boolean islastCorrectTime=GainNewDateUtil.compare_date(pubDateStr, lastTime);
		    if (islastNow && islastCorrectTime) {
			if (isdescription) {
			    beanlist.add(new ZdcNewsInfo(title, "新浪",
				    description, pubDateStr, urlName,
				    CATEGORY[index]));
			}
		    }
		} else {
		    if (islastNow) {
			if (isdescription) {
			    beanlist.add(new ZdcNewsInfo(title, "新浪",
				    description, pubDateStr, urlName,
				    CATEGORY[index]));
			}
		    }
		}
		}
	    }
    }
    
    private void insertTime(Set<List<ZdcNewsInfo>> set)
    {
	List<ZdcNewsInfo> resultInfo = new ArrayList<ZdcNewsInfo>();
	Iterator<List<ZdcNewsInfo>> mapIterator = set.iterator();
	while (mapIterator.hasNext()) {
	    Iterator<ZdcNewsInfo> newinfo = mapIterator.next().iterator();
	    while (newinfo.hasNext()) {
		ZdcNewsInfo info = newinfo.next();
		String infobuffer = gainNewsContent(info.getUrl());
		if (!infobuffer.equals("NO")) {
		    if (!info.getDescription().equals(null)|| info.getDescription() != "") {
			resultInfo.add(new ZdcNewsInfo(info.getTitle(), info .getAuthor(), info.getDescription(), info .getPubDate(), infobuffer.toString(), info .getUrl(), info.getType()));
		    }
		}
	    }
	}
	if (resultInfo.size() != 0) {
	    if (type == 0) {
		zdcNewsTotalUpdateService.insert(resultInfo);
	    } else {
		zdcNewsUpdateService.insert(resultInfo);
	    }
	}
    }

    private String gainNewsContent(String url) {
	StringBuffer buffer = null;
	try {
	    buffer = new StringBuffer();
	    org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
	    org.jsoup.nodes.Element e = doc.getElementById("artibody");
	    if (e.hasText()) {
		Elements item = e.getElementsByTag("p");
		Iterator<org.jsoup.nodes.Element> it = item.iterator();
		while (it.hasNext()) {
		    org.jsoup.nodes.Element p = it.next();
		    buffer.append(p.text() + "\n");
		}
	    }
	} catch (Exception e) {
	    buffer = new StringBuffer("NO");
	}
	return buffer.toString();
    }
}
