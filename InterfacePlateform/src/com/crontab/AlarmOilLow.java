package com.crontab;


import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;

import com.db.dto.TZdcOilAlarm;
import com.db.dto.TZdcTerminalOnlineflag;
import com.db.dto.ZdcCanstream;
import com.db.dto.ZdcItovTerminal;
import com.db.service.TZdcDataitemService;
import com.db.service.ZdcCanstreamService;
import com.db.service.ZdcItovTerminalService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.jpush.examples.JPushClientExample;
import com.system.SpringApplicationContextFactory;
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class AlarmOilLow implements Job {
  
	private static ApplicationContext ctx = SpringApplicationContextFactory.newInstance();
	private static ZdcItovTerminalService zdcItovTerminalService;
	private static TZdcDataitemService adcDateItemService;
	private static ZdcCanstreamService zdcCanstreamService;
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
		autoRun();
	}

	public static void main(String[] args) {
		autoRun();
	}
	private static void autoRun()
	{

		zdcItovTerminalService = ctx.getBean(ZdcItovTerminalService.class);
		zdcCanstreamService= ctx.getBean(ZdcCanstreamService.class);
		adcDateItemService = ctx.getBean(TZdcDataitemService.class);
		// 1)得到在线设备信息
		List<ZdcItovTerminal> zdcItovTerminal = zdcItovTerminalService.selectAllForState();
		if(zdcItovTerminal!=null && zdcItovTerminal.size()>0)
		{
			for(ZdcItovTerminal zdcTerminal:zdcItovTerminal)
			{
				String terminalImei = zdcTerminal.getTerminalImei();
				//查看车机是否在线
				TZdcTerminalOnlineflag terminalOnlineFlag = adcDateItemService.selectInfoByImei(terminalImei);
				//如果在线则下发短信
				if(null !=terminalOnlineFlag && "1".equals(terminalOnlineFlag.getOnlimneStatus()))
				{
					//根据imei查询最新一条can数据
					ZdcCanstream zdcCanstream = zdcCanstreamService.selectCanByDeviceId(terminalImei);
					if(null != zdcCanstream)
					{
						
						
					    if(zdcCanstream.getResidualOilConsumption()>10)
					    {
					    	//根据车机imei查询此车辆信息是否存在油耗过低临界值信息
					    	List<TZdcOilAlarm> list = adcDateItemService.selectByTerminalImei(terminalImei);
					    	//如果存在则删除否则不进行操作
						    if(null != list && list.size()>0)
						    {
						    	adcDateItemService.deleteByImei(terminalImei);
						    }
					    }else
					    {
					    	if(zdcCanstream.getResidualOilConsumption()<=10&&zdcCanstream.getResidualOilConsumption()>5)
						    {
						    	TZdcOilAlarm tzdcOil = new TZdcOilAlarm();
						    	tzdcOil.setTerminalImei(terminalImei);
						    	tzdcOil.setOilValue(String.valueOf(10));
						    	//根据imei和油耗临界值 查询是否存在此车机的低于10的油耗预警数据
						    	TZdcOilAlarm tadcOil2 = adcDateItemService.selectByImeiOil(tzdcOil);
						    	if(null!=tadcOil2)
						    	{
						    		//油耗状态标识
						    		String flag = tadcOil2.getStatus();
						    		//如果==0则推送油耗剩余过低报警信息
						    		if("0".equals(flag))
						    		{
						    			//推送信息
						    			 double consumption = zdcCanstream.getResidualOilConsumption();
						    			 sendSms(terminalImei,"您的剩余油耗:"+(int)consumption+","+tadcOil2.getId(),"油耗过低预警");
						    			
						    		}
						    		/*
						    		else if("1".equals(flag))
						    		{
						    			String uuid = UUID.randomUUID().toString();
						    			//如果是1则表示推送过了
						    			//如果不存在则插入一条数据
							    		TZdcOilAlarm tzdcOilInsert = new TZdcOilAlarm();
							    		tzdcOilInsert.setTerminalImei(terminalImei);
							    		tzdcOilInsert.setOilValue("10");
							    		tzdcOilInsert.setStatus("0"); 
							    		adcDateItemService.insert(tzdcOilInsert);
						    			
						    		}else if("2".equals(flag))
						    		{
						    			//如果是2则不再推送
						    			
						    			
						    		}*/
						    	}else
						    	{
						    		//如果不存在则插入一条数据
						    		TZdcOilAlarm tzdcOilInsert = new TZdcOilAlarm();
						    		tzdcOilInsert.setTerminalImei(terminalImei);
						    		tzdcOilInsert.setOilValue("10");
						    		tzdcOilInsert.setStatus("0");
						    		tzdcOilInsert.setUploadTime(new Date());
						    		adcDateItemService.insert(tzdcOilInsert);
						    		TZdcOilAlarm tadcOil245 = adcDateItemService.selectByImeiOil(tzdcOilInsert);
						    		double consumption = zdcCanstream.getResidualOilConsumption();
						    		//推送信息给用户
						    		sendSms(terminalImei,"您的剩余油耗:"+(int)consumption+","+tadcOil245.getId(),"油耗过低预警");
						    	}
						    }
						    //
					    	else if(zdcCanstream.getResidualOilConsumption()<=5)
						    {
						    	TZdcOilAlarm tzdcOil = new TZdcOilAlarm();
						    	tzdcOil.setTerminalImei(terminalImei);
						    	tzdcOil.setOilValue(String.valueOf(5));
						    	//根据imei和油耗临界值 查询是否存在此车机的低于10的油耗预警数据
						    	TZdcOilAlarm tadcOil2 = adcDateItemService.selectByImeiOil(tzdcOil);
						    	if(null!=tadcOil2)
						    	{
						    		//油耗状态标识
						    		String flag = tadcOil2.getStatus();
						    		//如果=0则推送油耗剩余过低报警信息
						    		if("0".equals(flag))
						    		{
						    			//
						    			double consumption = zdcCanstream.getResidualOilConsumption();
						    			sendSms(terminalImei,"您的剩余油耗:"+(int)consumption+","+tadcOil2.getId(),"油耗过低预警");
						    			
						    		}/*else if("1".equals(flag))
						    		{
						    			//如果是1则表示推送过了
						    			
						    		}else if("2".equals(flag))
						    		{
						    			//如果是2则不再推送
						    		}*/
						    	}else
						    	{
						    		//如果不存在则插入一条数据
						    		TZdcOilAlarm tzdcOilInsert = new TZdcOilAlarm();
						    		tzdcOilInsert.setTerminalImei(terminalImei);
						    		tzdcOilInsert.setOilValue("5");
						    		tzdcOilInsert.setStatus("0"); 
						    		tzdcOilInsert.setUploadTime(new Date());
						    		adcDateItemService.insert(tzdcOilInsert);
						    		TZdcOilAlarm tadcOil5 = adcDateItemService.selectByImeiOil(tzdcOilInsert);
						    		//推送信息给用户
						    		double consumption = zdcCanstream.getResidualOilConsumption();
						    		sendSms(terminalImei,"您的剩余油耗:"+(int)consumption+","+tadcOil5.getId(),"油耗过低预警");
						    	}
						    }
					    	
					    }
						
					}
					
				}
			}
		}
		
		
		
		/*
		zdcItovTerminalService = ctx.getBean(ZdcItovTerminalService.class);
		zdcCanstreamService= ctx.getBean(ZdcCanstreamService.class);
		adcDateItemService = ctx.getBean(TZdcDataitemService.class);
		// 1)得到在线设备信息
		List<ZdcItovTerminal> zdcItovTerminal = zdcItovTerminalService.selectAllForState();
		if(zdcItovTerminal!=null && zdcItovTerminal.size()>0)
		{
			for(ZdcItovTerminal zdcTerminal:zdcItovTerminal)
			{
				String terminalImei = zdcTerminal.getTerminalImei();
				//根据imei查询最新一条can数据
				ZdcCanstream zdcCanstream = zdcCanstreamService.selectCanByDeviceId(terminalImei);
				if(null != zdcCanstream)
				{
					
					if(null != zdcCanstream.getResidualOilConsumptionState())
					{
						//如果有油耗过低状态值则发送预警信息
						if(zdcCanstream.getResidualOilConsumptionState()==1)
						{
						    sendSms(terminalImei,"您的剩余油耗:"+zdcCanstream.getResidualOilConsumption(),"油耗过低预警");
						}else
						{
							//查询油耗过低预警标识
							TZdcDataitem zdcItem = adcDateItemService.selectByPrimaryKey(StaticStr.RESIDUAL_OIL_STATE);
							if(null != zdcItem)
							{
								String dataItemName = zdcItem.getItemName();
								//如果非0则推送油耗过低消息
								if(Integer.parseInt(dataItemName)!=0)
								{
									sendSms(terminalImei,"您的剩余油耗:"+zdcCanstream.getResidualOilConsumption(),"油耗过低预警");
								}
							}
						}
					}
					if(null != zdcCanstream.getBatteryVoltageState())
					{
						//如果电压过低的状态为1则下发短信
						if(zdcCanstream.getBatteryVoltageState()==1)
						{
							sendSms(terminalImei,"您的电压:"+zdcCanstream.getBatteryVoltage(),"电压过低预警");
						}else
						{
							//如果电压过低的标识非0 则下发短信
							TZdcDataitem zdcItemVolTage = adcDateItemService.selectByPrimaryKey(StaticStr.BATTERY_VOLTAGE_STATE);
							if(null !=zdcItemVolTage && zdcCanstream!=null)
							{
								String dataItemName = zdcItemVolTage.getItemName();
								//如果非0则推送电压过低消息
								if(Integer.parseInt(dataItemName)!=0)
								{
									sendSms(terminalImei,"您的电压:"+zdcCanstream.getBatteryVoltage(),"电压过低预警");
								}
							}
						}
					}
				}
				
				
			}
		}
	*/}
	//下发短信方法
	private static void sendSms(String terminal,String content,String title)
	{
		JsonFactory jfactory = new JsonFactory();
		StringWriter jsonWrite = new StringWriter();
		JsonGenerator json;
		try {
			json = jfactory.createJsonGenerator(jsonWrite);
			json.writeStartObject(); 
			json.writeFieldName("body");
			json.writeStartObject(); 
	        json.writeStringField("Msgtype","102");//信息类型 0-广告 1-导航 2-音乐 3-视频   4-路况 5-新闻  6-故障码  102-油耗预警
	        json.writeStringField("Title",title);//标题
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
        retStr= JPushClientExample.SendMsg(terminal,title,bodyResponseJson);
	}
}
