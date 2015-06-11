package com.crontab;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.db.dto.ZdcCanBusstream;
import com.db.dto.ZdcCanstream;
import com.db.dto.ZdcCanstreamOriginal;
import com.db.service.ZdcCanstreamOriginalService;
import com.db.service.ZdcCanstreamService;
import com.main.Common;
import com.system.SpringApplicationContextFactory;
// @DisallowConcurrentExecution 此标记用在实现Job的类上面,意思是不允许并发执行,按照我之前的理解是 不允许调度框架在同一时刻调用Job类，后来经过测试发现并不是这样，而是Job(任务)的执行时间[比如需要10秒]大于任务的时间间隔 [Interval（5秒)],那么默认情况下,调度框架为了能让 任务按照我们预定的时间间隔执行,会马上启用新的线程执行任务。否则的话会等待任务执行完毕以后 再重新执行！（这样会导致任务的执行不是按照我们预先定义的时间间隔执行）
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CronAutoClacCanStream implements Job {
	private static final Logger log = Logger.getLogger(CronAutoClacCanStream.class.getName());
	private static ApplicationContext ctx = SpringApplicationContextFactory.newInstance();
	ZdcCanstream zdcCanstream = new ZdcCanstream();
	static ZdcCanstreamOriginalService zdcCanstreamOriginalService;
	static ZdcCanstreamService zdcCanstreamService;
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		autoRun();
	}
	public static void main(String[] args) {
		autoRun();
	}
	private static void autoRun() {
		zdcCanstreamOriginalService = ctx.getBean(ZdcCanstreamOriginalService.class);
		zdcCanstreamService = ctx.getBean(ZdcCanstreamService.class);
		String ids="";
		List list_ids=new ArrayList();
		List<ZdcCanstreamOriginal> list = zdcCanstreamOriginalService.select();
		List<ZdcCanstream> stream_list=new ArrayList<ZdcCanstream>();
		String stream = "";// 数据流信息
		String deviceid = "";// 设备ID
		long id = 0;// 数据流ID
		String main_arr0[] = null;//
		String main_arr[] = null;// 指令文本
		String sec_arr[] = null;// 单个指令
		long begin3 =0;
		if(!list.isEmpty())
		{
			log.info("开始解析CAN数据:##########################");
			begin3 = System.currentTimeMillis();
		}
		for (ZdcCanstreamOriginal zdcCanstreamOriginal1 : list) {
			stream = zdcCanstreamOriginal1.getStream();
			deviceid = zdcCanstreamOriginal1.getDeviceid();
			id = zdcCanstreamOriginal1.getId();
			list_ids.add(id);
			//log.info("================数据流=====================");
			//log.info(stream);
			/*	log.info("================设备ID=====================");
			log.info(deviceid);
			log.info("================数据流ID=====================");
			log.info(id);*/
			// 2E8101017C#2E16020000&*#2014-10-21
			// 10:10;2E410201AF0C#2E410D0200000DAC04B000B400138819DA&*#2014-10-21
			// 10:10;
			ZdcCanstream zdcCanstream = new ZdcCanstream();// 存解析的CAN数据
			main_arr0 = stream.split(";");
			if (main_arr0.length > 0) {
				List<ZdcCanBusstream> streamBus = new ArrayList<ZdcCanBusstream>();
				for (String str_main0 : main_arr0) {
					main_arr = str_main0.split("#");
					String str_date = "";
					if (main_arr.length >= 0) {
						for (String str_Instruction : main_arr) {
							if (str_Instruction.contains(" "))// 取时间
							{
								str_date = str_Instruction;
								continue;
							}
							if (str_Instruction.length() > 2)// 如果开始位不为 2E的丢掉
							{
								if (!str_Instruction.subSequence(0, 2).equals("2E"))
									continue;
							}
							if (str_Instruction.length() >= 8)// 长度大于8的才有意义
							{
								sec_arr = Common.arr(str_Instruction);
								if(sec_arr!=null)//指令格式不正确
								{
									
								
								Integer type = Integer.valueOf(str_Instruction.substring(2, 4));
								switch (type) {
								// 设备全部信息 2E 71 04 00 01 02 03 00 7E
								/* 2E 71 04 00 01 02 03 00 7E */
								case 71:
									streamBus.add(new ZdcCanBusstream(deviceid, str_date, str_Instruction));
									break;
/*								case 81:// 车机启动
									zdcCanstream.setIgnition(1);// 发动机点火
									break;*/
								case 16:// 车速 2E16020000E7 (Data0+(Data1<<8))/16
									if (Common.calcHex(sec_arr) == 1) {
										/*
										 * byte[] b_16 =
										 * Common.toStringHex(sec_arr
										 * [2]+sec_arr[3]); double speed=
										 * (b_16[0]+(b_16[1]<<8))/16 ;//计算车速度
										 */
										double speed = 0;
										Integer sp1 = Integer.valueOf(sec_arr[3], 16);
										Integer sp2 = Integer.valueOf(sec_arr[2], 16);
										speed = (sp2 * 256 + sp1) / 100;
										zdcCanstream.setSpeed(speed);
									}
									break;
								case 21:// 空调信息

									break;
									
								case 24:// 基本信息
									if (Common.calcHex(sec_arr) == 1) {
										String b_24_01 = Common.hexString2binaryString(sec_arr[2]);
										if (b_24_01.length() == 8) {
											zdcCanstream.setReversing((Integer.valueOf(b_24_01.substring(5, 6))));// 倒车状态
											zdcCanstream.setBrake((Integer.valueOf(b_24_01.substring(6, 7))));// 刹车状态
											zdcCanstream.setLight((Integer.valueOf(b_24_01.substring(7, 8))));// 灯光状态
										}
										String b_24_02 = Common.hexString2binaryString(sec_arr[3]);
										if (b_24_02.length() == 8) {
											zdcCanstream.setBefortCover((Integer.valueOf(b_24_02.substring(2, 3))));// 前车盖
											zdcCanstream.setTailBoxDoor((Integer.valueOf(b_24_02.substring(3, 4))));// 尾箱门
											zdcCanstream.setRightAfterDoor((Integer.valueOf(b_24_02.substring(4, 5))));// 右后门
											zdcCanstream.setLeftAfterDoor((Integer.valueOf(b_24_02.substring(5, 6))));// 左后门
											zdcCanstream.setRigthBeforeDoor((Integer.valueOf(b_24_02.substring(6, 7))));// 右前门
											zdcCanstream.setLeftBeforeDoor((Integer.valueOf(b_24_02.substring(7, 8))));// 左前门
										}
									}
									break;
								case 25:// 泊车辅助状态

									break;
								case 26:// 方向盘信息

									break;
								/* 车身信息 */
								case 41:
									/* [41, 05, 04, 00, 00, 49, 41, 2B] */
									/* 数据校验 */
									if (Common.calcHex(sec_arr) == 1) {
										/*
										 * 数据校验OK 第一位 类型 第二位 长度 第三位 状态类型
										 */
										String h_41 = sec_arr[2];
										switch (Integer.valueOf(h_41)) {
										/* 安全带、清洗液、刹车、车门等状态 */
										case 01:
											String b_41_01 = Common.hexString2binaryString(sec_arr[3]);
											if (b_41_01.length() == 8) {
												zdcCanstream.setSafetyBelt((Integer.valueOf(b_41_01.substring(0, 1))));// 是否系安全带
												zdcCanstream.setCleaningLiquid((Integer.valueOf(b_41_01.substring(1, 2))));// 清洗液状况
												zdcCanstream.setBrake((Integer.valueOf(b_41_01.substring(2, 3))));// 刹车状态
												zdcCanstream.setTailBoxDoor((Integer.valueOf(b_41_01.substring(3, 4))));// 尾箱门
												zdcCanstream.setRightAfterDoor((Integer.valueOf(b_41_01.substring(4, 5))));// 右后门
												zdcCanstream.setLeftAfterDoor((Integer.valueOf(b_41_01.substring(5, 6))));// 左后门
												zdcCanstream.setRigthBeforeDoor((Integer.valueOf(b_41_01.substring(6, 7))));// 右前门
												zdcCanstream.setLeftBeforeDoor((Integer.valueOf(b_41_01.substring(7, 8))));// 左前门
											}
											break;
										/* 发动机转速、瞬时速度、车外温度、行车里程剩余油量、电压电池等信息 */
										case 02:
											double engine_Speed = Double.valueOf(hexConver(sec_arr[3]) * 256 + hexConver(sec_arr[4]));//发动机转速
											double speed_instant = Double.valueOf(hexConver(sec_arr[5]) * 256 + hexConver(sec_arr[6])) * 0.01;//瞬时速度
											double batteryVoltage = Double.valueOf(hexConver(sec_arr[7]) * 256 + hexConver(sec_arr[8])) * 0.01;//电池电压
											double outsideTemperature=0.0;
											if(sec_arr[9].equals("FF"))
											{
												outsideTemperature = Double.valueOf(hexConver(sec_arr[10])-255) * 0.1;//车外温度  室外温度为零下
											}
											else
											{
											  outsideTemperature = Double.valueOf(hexConver(sec_arr[9]) * 256 + hexConver(sec_arr[10])) * 0.1;//车外温度
											}
											double mileage = Double.valueOf(Integer.valueOf(hexConver(sec_arr[11]) * 65536 + hexConver(sec_arr[12]) * 256 + hexConver(sec_arr[13])));//行车里程
											zdcCanstream.setEngineSpeed(engine_Speed);
											zdcCanstream.setInstantaneousVelocity(speed_instant);
											zdcCanstream.setBatteryVoltage(batteryVoltage);
											zdcCanstream.setOutsideTemperature(outsideTemperature);
											zdcCanstream.setMileage(mileage);
											zdcCanstream.setResidualOilConsumption(Double.valueOf(hexConver(sec_arr[14])));
											break;
										/* 剩余油量过低、电池电压过低等报警 */
										case 03:
											String b_41_03 = Common.hexString2binaryString(sec_arr[3]);
											if (b_41_03.length() == 8) {
												zdcCanstream.setResidualOilConsumptionState((Integer.valueOf(b_41_03.toString().substring(0, 1))));// 剩余油耗过低报警
												zdcCanstream.setBatteryVoltageState((Integer.valueOf(b_41_03.toString().substring(1, 2))));// 电池电压过低报警
											}
											break;
										/* 车窗开度 */
										case 04:
											zdcCanstream.setLeft_Before_Window(hexConver(sec_arr[3]) / 2);
											zdcCanstream.setLeft_After_Window(hexConver(sec_arr[4]) / 2);
											zdcCanstream.setRigth_Before_Window(hexConver(sec_arr[5]) / 2);
											zdcCanstream.setRight_After_Window(hexConver(sec_arr[6]) / 2);
											break;
											/*车辆启动和停止  01:启动   00:停止*/
										case 05:
											zdcCanstream.setIgnition(hexConver(sec_arr[3]));
											break;
										default:
											break;
										}
									}
									break;
								default:
									break;
								}
								}
							}
						}
						zdcCanstream.setDeviceuid(deviceid);
						zdcCanstream.setStr_uploadTime(str_date);
						if(zdcCanstream.getIgnition() == null)
						{
							zdcCanstream.setIgnition(-1);
						}
						if ((zdcCanstream.getInstantaneousVelocity() == null) && (zdcCanstream.getIgnition() == -1)) {
							// 瞬时速度不为NULL的情况下，才会插入数据库
						} else {
								//zdcCanstreamService.insert(zdcCanstream);
								stream_list.add(zdcCanstream);
						}
					}
				}
				/* 插入总线信息 */
				if (streamBus.size() != 0) {
					zdcCanstreamService.insertBus(streamBus);
				}
				// 修改已读标记
				//zdcCanstreamOriginalService.updateByPrimaryKeySelective(id);
			}
		}
		// 修改已读标记
		if(!list_ids.isEmpty())
		{
		    zdcCanstreamOriginalService.updateByPrimaryKeySelectives(list_ids);
		}
		if(!stream_list.isEmpty())
		{
		    new FileUtil().canstream_writeFileForDB("can"+DateUtil.getFileName().toString(), stream_list);//写入文本
		 	long end3 = System.currentTimeMillis();
		 	System.out.println("解析CAN数据执行耗时:" + (end3 - begin3) + "豪秒");
		}
	}
	/* 16 进制字符串转换成十进制字符串 */
	private static int hexConver(String hexStr) {
		int result = Integer.valueOf(hexStr, 16);
		return result;
	}

	/**
	 * 日期字符串准换成日期对象
	 * 
	 * @param dateStr
	 * @return <Date>date
	 */
	private static Date dateParse(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			log.info("日期解析错误;<解析失败|日期格式有误> 错误信息:" + e.getMessage());
		}
		return date;
	}

}
