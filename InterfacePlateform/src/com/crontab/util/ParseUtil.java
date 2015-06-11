package com.crontab.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.crontab.CronAutoNewsGainer;
import com.db.dto.ZdcNewsInfo;

public class ParseUtil {
    private static final Logger log = Logger.getLogger(ParseUtil.class
	    .getName());

    public List<ZdcNewsInfo> gainNewsLetterSimgle(String type, String url)
	    throws DocumentException, IOException, ParseException {
	DateUtil dateUtil = new DateUtil();
	SAXReader reader = new SAXReader();
	List node = reader.read(url).getRootElement().element("channel")
		.elements("item");
	Iterator items = node.iterator();
	List<ZdcNewsInfo> newsLitter = new ArrayList<ZdcNewsInfo>();
	while (items.hasNext()) {
	    Element iteme = (Element) items.next();
	    String urlName = iteme.element("link").getTextTrim();
	    String linkType = urlName.substring(urlName.lastIndexOf(".") + 1);
	    if (linkType.equals("shtml")) {
		String pubDate = dateUtil.dateConvert(iteme.element("pubDate")
			.getTextTrim());
		StringBuffer contentStr = gainNewContent(urlName);
		if (dateUtil.dateCompare(pubDate)) {
		    if (!contentStr.toString().equals("T")) {
			newsLitter.add(new ZdcNewsInfo(iteme.element("title")
				.getTextTrim(), iteme.element("author")
				.getTextTrim(), iteme.element("description")
				.getTextTrim().replace("，", ","), pubDate,
				contentStr.toString(), type));
		    }
		}
	    }
	}
	return newsLitter;
    }

    public List<ZdcNewsInfo> gainNewsLetterSimgle_Total(String type, String url)
	    throws DocumentException, IOException, ParseException {
	DateUtil dateUtil = new DateUtil();
	/* 实例化解析器 */
	SAXReader reader = new SAXReader();
	/* 加载数据源 */
	Document document = reader.read(url);
	Element rootElement = document.getRootElement();
	List node = rootElement.element("channel").elements("item");
	Iterator items = node.iterator();
	List<ZdcNewsInfo> newsLitter = new ArrayList<ZdcNewsInfo>();
	int index = 0;
	while (items.hasNext()) {
	    if (index == 2) {
		break;
	    } else {
		Element iteme = (Element) items.next();
		String urlName = iteme.element("link").getTextTrim().toString();
		// 如果URL结尾是XHTML的执行解析，否则不处理
		String linkType = urlName
			.substring(urlName.lastIndexOf(".") + 1);
		if (linkType.equals("shtml")) {
		    // 与当前时间比对取最新时间的新闻
		    String pubDate = dateUtil.dateConvert(iteme.element("pubDate")
			    .getTextTrim());
		    StringBuffer contentStr = gainNewContent(urlName);
		    if (dateUtil.dateCompare(pubDate)) {
			if (!contentStr.toString().equals("T")) {
			    // 增加与当前时间比对处理方法
			    newsLitter.add(new ZdcNewsInfo(iteme.element(
				    "title").getTextTrim(), iteme.element(
				    "author").getTextTrim(), iteme
				    .element("description").getTextTrim()
				    .replace("，", ","), pubDate, contentStr
				    .toString(), type));
			}
		    }
		}
	    }
	    index++;
	}
	return newsLitter;
    }

    public StringBuffer gainNewContent(String url) {
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
	    buffer = new StringBuffer("T");
	}
	return buffer;
    }

}
