package com.crontab;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;

import com.db.dto.TZdcBreakrules;
import com.db.service.TzdcBreakRulesService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.jpush.examples.JPushClientExample;
import com.system.SpringApplicationContextFactory;
/**
 * 车辆违章信息推送
 * @author Administrator
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class BreakRulesSmsPush implements Job {
	private static final Logger log = Logger.getLogger(BreakRulesSmsPush.class.getName());
	private static ApplicationContext ctx = SpringApplicationContextFactory.newInstance();
	static TzdcBreakRulesService zdcBreakRuleService;

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		autoRun();
	}

	public static void main(String[] args) {
		autoRun();
	}

	private static void autoRun() {
		zdcBreakRuleService = ctx.getBean(TzdcBreakRulesService.class);
		log.info("车辆违章信息定时任务~~~~~~~~~~~~~~~~BreakRulesSmsPush");
		// TODO Auto-generated method stub
		//查询全部手机号列表
		List<String> accountList = zdcBreakRuleService.qryTelList();
		for(String account_name:accountList)
		{
			//根据手机号查询未阅读的违章信息
			List<TZdcBreakrules> brakRuleList = zdcBreakRuleService.qryBreakRuleListByTel(account_name);
			String content = "";
			int temp = 0;
			for(TZdcBreakrules breakrule:brakRuleList)
			{
				if(temp<=0)
				{
					content = content+breakrule.getId();
				}else
				{
					content = content+","+breakrule.getId();
				}
				
				temp++;
				
			}
			 JsonFactory jfactory = new JsonFactory();
				StringWriter jsonWrite = new StringWriter();
				JsonGenerator json;
				try {
					json = jfactory.createJsonGenerator(jsonWrite);
					json.writeStartObject(); 
					json.writeFieldName("body");
					json.writeStartObject(); 
			        json.writeStringField("Msgtype","100");//信息类型 0-广告 1-导航 2-音乐 3-视频   4-路况 5-新闻  6-故障码 
			        json.writeStringField("Title","违章信息");//标题
			        json.writeStringField("Imageurl","");//图片url
			        json.writeStringField("Noticeurl","");//广告url
			        json.writeStringField("Content",content);//内容
			        json.writeStringField("strType", "");//导航类型 0-最快路线 1-最短路线 2-避开高速 3-步行
			        json.writeStringField("strNum", "");
					json.writeEndObject(); 
					json.writeEndObject(); 
					json.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String bodyResponseJson = jsonWrite.toString();
				String retStr="";
		        retStr= JPushClientExample.SendMsg(account_name,"违章信息",bodyResponseJson);
		        log.info(account_name+"~~~~违章消息推送retStr:~~~"+retStr);
		    	
		}
		
	}


}
