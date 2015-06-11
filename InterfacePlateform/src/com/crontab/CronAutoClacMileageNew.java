package com.crontab;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;

import com.db.dto.ZdcCanstream;
import com.db.dto.ZdcItovTerminal;
import com.db.dto.ZdcMileage;
import com.db.service.ZdcCanstreamService;
import com.db.service.ZdcItovTerminalService;
import com.db.service.ZdcMileageService;
import com.system.SpringApplicationContextFactory;

/**
 * 自动计算里程服务 1、查询所有在线的设备号 2、通过设备号查询CAN数据 3、select id from t_zdc_canstream where
 * deviceid=? and Engine_Speed=0 and Mileage_Finish=0 order by Upload_Time asc
 * limit 1; //转速为0是点火开始 ，没 有计算里程的 4、select id from t_zdc_canstream where
 * deviceid=? and Engine_Speed=0 and Mileage_Finish=0 and id>(步骤3中得到的ID) order
 * by Upload_Time asc limit 1 5、select * from t_zdc_canstream where id>=(步骤3的ID)
 * and id<(步骤4的ID) order by Upload_Time asc 6、循环步骤5中得到的数据 ，计算入库
 * 
 * @author dell
 * 
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CronAutoClacMileageNew implements Job {
	private static final Logger log = Logger.getLogger(CronAutoClacMileageNew.class.getName());
	private static ApplicationContext ctx = SpringApplicationContextFactory.newInstance();
	private static ZdcItovTerminalService zdcItovTerminalService;
	private static ZdcMileageService zdcMileageService;
	private static ZdcCanstreamService zdcCanstreamService;

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		autoRun();
	}

	
	private void autoRun() {
		zdcItovTerminalService = ctx.getBean(ZdcItovTerminalService.class);
		zdcMileageService = ctx.getBean(ZdcMileageService.class);
		
		zdcCanstreamService = ctx.getBean(ZdcCanstreamService.class);
		//log.info("================ CAN 服务启动=====================");
		// 1)得到在线设备信息
		List<ZdcItovTerminal> zdcItovTerminal = zdcItovTerminalService.selectAllForState();
		System.out.println("CronAutoClacMileage");
		long id_lower = 0;
		long id_heigth = 0;
		log.info("================ CAN 数据流=====================");

		for (ZdcItovTerminal zdc : zdcItovTerminal) {
			
			
			ZdcCanstream zdcCanstream = new ZdcCanstream();
			ZdcCanstream zdcCanstream1 = new ZdcCanstream();
			ZdcCanstream zdcCanstream2 = new ZdcCanstream();
			//System.out.println(zdc.getTerminalImei());
			String tt = zdc.getTerminalImei();
			//log.info("================ IEMI号 :"+tt+"=====================");
			zdcCanstream.setDeviceuid(tt);
			/*Long tempid = zdcCanstreamService.selectIdByDeviceId(tt);  //根据设备id查询上一个里程id
			if(null !=tempid)
			{
				zdcCanstream.setId1(tempid);
			}*/
			// 2)得到点火ID
			zdcCanstream = zdcCanstreamService.selectForID1(zdcCanstream);
			if (zdcCanstream != null) {
				id_lower = zdcCanstream.getId();
				
				//log.info("================ id_lower :"+id_lower+"=====================");
				//System.out.println(id_lower);
				zdcCanstream1.setId(id_lower);
				zdcCanstream1.setDeviceuid(zdc.getTerminalImei());
			}
			// 3)得到熄火ID
			zdcCanstream1 = zdcCanstreamService.selectForID2(zdcCanstream1);
			if (zdcCanstream1 != null) {
				id_heigth = zdcCanstream1.getId();
				//log.info("================ id_heigth :"+id_heigth+"=====================");
				//System.out.println(id_heigth);
				zdcCanstream2.setId(id_lower);
				zdcCanstream2.setId1(id_heigth);
				zdcCanstream2.setDeviceuid(zdc.getTerminalImei());
			}
		  
			// 4)得到点火和熄火之间的数据流信息
			List<ZdcCanstream> list = zdcCanstreamService.selectForID3(zdcCanstream2);
			int size = list.size();
			//计算怠速次数初始化为0
			int waitCount = 0;
			//计算本次行驶里程信息
			double mileage = 0;
			Date startTime = null ;
			Date endTime = null;
			if (list != null && list.size()>0) {
				  //如果存在id则更新熄火的id到数据库 否则插入20141205
				/*ZDCanstreamTempSave tempCanstream = new ZDCanstreamTempSave();
				tempCanstream.setDeviceid(tt);
				tempCanstream.setId(id_heigth);
				if(null ==tempid)
				{
					zdcCanstreamService.insertCanStreamID(tempCanstream);
				}else
				{
					zdcCanstreamService.updateCanStreamID(tempCanstream);
				}*/
				ZdcMileage zdcMile = new ZdcMileage();
				//里程开始
				Double mileStart = null;//= 0;
				//里程结束
				Double mileEnd = null;// = 0;
				Double oilBegin = null;// = 0;  //起始油耗
				Double oilEnd = null;// = 0 ;   //结束油耗
			   //临时变量用来保存有几条数据
			   int temp = 0;
			   for (ZdcCanstream l : list) {
				   if(l!=null)
				   {
					   temp++;
					   //当里程只有两条信息点火和熄火时
					   if(size==2)
					   {
						   if(temp==1)
						   {
							   mileStart = l.getMileage();  //开始的里程时间
							   startTime = l.getUploadTime(); //获取起始时间
							   oilBegin = l.getResidualOilConsumption();
							 
						   }
						   else
						   {
							 //结束里程
								mileEnd = l.getMileage();
								//结束时间
								endTime=l.getUploadTime();
								oilEnd = l.getResidualOilConsumption();
						   }
						   int t = zdcCanstreamService.updateMileFlag(l.getId());
					   
					   }else
					   {
						   //System.out.println("speed-->>"+l.getSpeed());
							Double speed = l.getSpeed();  //怠速
							/*//更新第一个里程标记
							if(Math.abs(l.getEngineSpeed()) <= 1e-15 &&"0".equals(l.getMileageFinish()))
							{
								int t = zdcCanstreamService.updateMileFlag(l.getId());
								log.info("t>0更新成功"+t+"更新的id----->>"+l.getId());
							}*/
							if(temp == 1)
							{
								
								zdcCanstreamService.updateMileFlag(l.getId());
								
							}
							if(temp==2)
							{
								mileStart = l.getMileage();  //开始的里程时间
								startTime = l.getUploadTime(); //获取起始时间
								oilBegin = l.getResidualOilConsumption();
							}
							if(temp==(size-1))
							{
								//结束里程
								mileEnd = l.getMileage();
								//结束时间
								endTime=l.getUploadTime();
								oilEnd = l.getResidualOilConsumption();
								zdcCanstreamService.updateMileFlag(l.getId());
							}
							////如果速度为0则怠速次数加1
							if(speed == null || Math.abs(speed) <= 1e-15)
							{
								waitCount++;
							}
					   }
					   
						Double mile = l.getMileage();   //里程信息
						/*//如果里程为空则临时赋值为0 方便后面操作
						if(mile== null)
						{
							mile = 0.0;
						}
						mileage = mileage + mile;*/
					
				   }
				}
			   if(mileStart==null)
			   {
				   mileStart = 0.0;
			   }
			   if(mileEnd ==null)
			   {
				   mileEnd = 0.0;
			   }
			   
			    mileage = mileEnd - mileStart;
				zdcMile.setDatastreamid2(String.valueOf(Math.abs(mileage)));//行驶里程
			  
			    if(endTime != null && startTime != null)
				{
				    long totalsec = (endTime.getTime() - startTime.getTime())/1000;  //计算为秒
				    long min = totalsec/60;  //消耗的分钟数
				    long sec = totalsec%60;   //剩余秒数
					StringBuffer sf = new StringBuffer();
					if(min>0)
					{
						sf.append(min);
						sf.append("分");
					}
					sf.append(sec);
					sf.append("秒");
					zdcMile.setDatastreamid1(sf.toString()) ; //使用时间
				 }
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//设置怠速次数
				zdcMile.setDatastreamid4(String.valueOf(waitCount));
				
				zdcMile.setDatastreamid10(String.valueOf(mileStart)); // 里程开始
				zdcMile.setDatastreamid11(String.valueOf(mileEnd));  //里程结束
				
				if(oilBegin == null)
				{
					oilBegin=0.0;
				}
				zdcMile.setDatastreamid12(String.valueOf(oilBegin));  //油耗开始
				
				if(oilEnd == null)
				{
					oilEnd =0.0;
				}
				zdcMile.setDatastreamid13(String.valueOf(oilEnd));  //油耗结束
				//设置油耗
				zdcMile.setDatastreamid3(String.valueOf(Math.abs(oilBegin - oilEnd)));
				
				if(startTime!=null)
				{
				zdcMile.setDatastreamid14(sdf.format(startTime));  //时间开始
				}
				if(endTime!=null)
				{
				zdcMile.setDatastreamid15(sdf.format(endTime));  //时间结束
				}
				zdcMile.setDeviceuid(zdc.getTerminalImei());  //设置设备id
				//插入里程
				try
				{
					zdcMileageService.insert(zdcMile);
				}catch(Exception e)
				{
					log.info("CronAutoClacMileage~~~里程信息插入异常");
					System.out.println("插入异常");
				}
				
				
			}
			/*ZdcCanstream zdcCanstream4 = new ZdcCanstream();
			zdcCanstream4.setId(id_lower);
			zdcCanstream4.setId1(id_heigth);
			zdcCanstream4.setDeviceuid(zdc.getTerminalImei());
			//查询本个里程中是否还有启动标识的
			List<ZdcCanstream> canList = zdcCanstreamService.selectUnfinished(zdcCanstream4);
			if(null !=canList && canList.size()>0)
			{
				for(ZdcCanstream zdccan:canList)
				{
					zdcCanstreamService.updateMileFlag(zdccan.getId());  //更新完成标识
					
				}
			}*/
		}
	}
	
	
	public static void main(String[] args) {
		new CronAutoClacMileageNew().autoRun();
	}
}