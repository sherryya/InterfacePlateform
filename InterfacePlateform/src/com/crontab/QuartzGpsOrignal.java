package com.crontab;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;

import com.crontab.util.DateUtil;
import com.crontab.util.FileUtil;
import com.db.dto.ZdcGpsinfo;
import com.db.dto.ZdcGpsinfoOriginal;
import com.db.service.ZdcGpsinfoOriginalService;
import com.db.service.ZdcGpsinfoService;
import com.mina.server.handle.MapUtil;
import com.system.SpringApplicationContextFactory;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzGpsOrignal implements Job {
	private static final Logger log = Logger.getLogger(QuartzGpsOrignal.class.getName());
	private static ApplicationContext ctx = SpringApplicationContextFactory.newInstance();
	static ZdcGpsinfoService zdcGpsinfoService;
	static ZdcGpsinfoOriginalService zdcGpsinfoOrigService;
	private static void quartGpsOriginal()
	{
		zdcGpsinfoOrigService = ctx.getBean(ZdcGpsinfoOriginalService.class);
		zdcGpsinfoService = ctx.getBean(ZdcGpsinfoService.class);
		//查询所有is_deal 为0的信息
		List<ZdcGpsinfoOriginal> list = zdcGpsinfoOrigService.select();
		List<ZdcGpsinfo> gpsList = new ArrayList<ZdcGpsinfo>();
		List listIDS = new ArrayList();
		String stream = "";//数据流信息
		String deviceid="";//设备ID
		//Date crt_date;//数据流上传时间
		long id=0;//数据流ID
		log.info("================ GPS QuartzGpsOrignal======数量:"+list.size()+"===============");
		for (ZdcGpsinfoOriginal zdcgps : list) {
			stream = zdcgps.getStream();
			//获取设备id
			deviceid=zdcgps.getDeviceid();
			//crt_date=zdcgps.getCrt_date();
			id=zdcgps.getId();
			listIDS.add(id);
/*			log.info("================ GPS 数据流=====================");
			log.info(stream);
			log.info("================设备ID=====================");
			log.info(deviceid);
			log.info("================数据流ID=====================");
			log.info(id);*/
			//stream的格式形如：00,11,22,33,44,55,66,77;
			String arrStream[] = stream.split(";");
			int streamLength = arrStream.length;
			for(int i=0;i<streamLength;i++)
			{
				ZdcGpsinfo zdcGpsinfo = new ZdcGpsinfo();
				//将每个串以逗号分隔
				String arr[] = arrStream[i].split(",");
				if (arr.length == 7) {
					if (arr[0].length() > 0) {
						zdcGpsinfo.setDeviceuid(deviceid);
						zdcGpsinfo.setDirection(arr[4].length() == 0 ? 0 : Double.valueOf(arr[4]));
						zdcGpsinfo.setGpstime(arr[6]);
						zdcGpsinfo.setHdop(arr[5].length() == 0 ? 0 : Double.valueOf(arr[5]));
						zdcGpsinfo.setHeight(arr[2].length() == 0 ? 0 : Double.valueOf(arr[2]));
						zdcGpsinfo.setLatitude(arr[0].length() == 0 ? 0 : Double.valueOf(arr[0]));
						zdcGpsinfo.setLongitude(arr[1].length() == 0 ? 0 : Double.valueOf(arr[1]));
						zdcGpsinfo.setSpeed(arr[3].length() == 0 ? 0 : Double.valueOf(arr[3]));
						// 添加到文件
						gpsList.add(zdcGpsinfo);
					}
				}				
			}
		}
		
		try {
			gpsList=MapUtil.getBaidudb09ll(gpsList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//gps文件
		if(!gpsList.isEmpty())
		{
			new FileUtil().gpsstream_writeFileForDB("gps"+DateUtil.getFileName().toString(), gpsList);//写入文本
		 	//long end3 = System.currentTimeMillis();
		}
		if(!listIDS.isEmpty())
		{
			//修改已读标记
			zdcGpsinfoOrigService.updateByPrimaryKeyIDS(listIDS);
		}
	}

	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		quartGpsOriginal();
	}
	public static void main(String[] args) {
		quartGpsOriginal();
	}}













//将数据插入
//zdcGpsinfoService.insert(zdcGpsinfo);

/*log.info("================ GPS 入mongodb 开始=====================");
DBCollection collection = Initialization.getInstance().getDb().getCollection("t_zdc_gpsinfo");
BasicDBObject bo = new BasicDBObject();
bo.put("deviceuid", deviceid);

bo.put("gpstime", arr[6]);
bo.put("latitude", arr[0].length() == 0 ? 0 : Double.valueOf(arr[0]));
bo.put("longitude", new Date());
bo.put("height", arr[1].length() == 0 ? 0 : Double.valueOf(arr[1]));
bo.put("speed", arr[3].length() == 0 ? 0 : Double.valueOf(arr[3]));
bo.put("direction", arr[4].length() == 0 ? 0 : Double.valueOf(arr[4]));
collection.insert(bo);
log.info("================ GPS 入mongodb 结束=====================");*/
