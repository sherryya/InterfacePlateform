package com.main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.db.dto.ZdcCanstream;
import com.db.dto.ZdcCanstreamOriginal;
import com.db.service.ZdcCanstreamOriginalService;
import com.db.service.ZdcCanstreamService;
import com.system.SpringApplicationContextFactory;
public class AutoClacCanStream extends HttpServlet {
	private static final Logger log = Logger.getLogger(AutoClacCanStream.class.getName());
	private static ApplicationContext ctx = SpringApplicationContextFactory.newInstance();
	ZdcCanstream zdcCanstream = new ZdcCanstream();
	ZdcCanstreamOriginalService zdcCanstreamOriginalService;
	ZdcCanstreamService zdcCanstreamService;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
	@Override
	public void init() throws ServletException {
		super.init();
		log.info(" =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= starts 开始解析数据=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ");
		while (true) {
			autoRun();
		}
	}
	private void autoRun() {
		zdcCanstreamOriginalService = ctx.getBean(ZdcCanstreamOriginalService.class);
		zdcCanstreamService = ctx.getBean(ZdcCanstreamService.class);
		List<ZdcCanstreamOriginal> list = zdcCanstreamOriginalService.select();
		String stream = "";// 数据流信息
		String deviceid = "";// 设备ID
		Date crt_date;// 数据流上传时间
		long id = 0;// 数据流ID
		String main_arr0[] = null;//
		String main_arr[] = null;// 指令文本
		String sec_arr[] = null;// 单个指令

		for (ZdcCanstreamOriginal zdcCanstreamOriginal1 : list) {
			stream = zdcCanstreamOriginal1.getStream();
			deviceid = zdcCanstreamOriginal1.getDeviceid();
			id = zdcCanstreamOriginal1.getId();
			log.info("================数据流=====================");
			log.info(stream);
			log.info("================设备ID=====================");
			log.info(deviceid);
			log.info("================数据流ID=====================");
			log.info(id);
			ZdcCanstream zdcCanstream = new ZdcCanstream();// 存解析的CAN数据
			main_arr0 = stream.split(";");
			if (main_arr0.length > 0) {
				for (String str_main0 : main_arr0) {
					main_arr = str_main0.split("2E");
					if (main_arr.length >= 0) {
						for (String str_Instruction : main_arr) {
							sec_arr = str_Instruction.trim().split(" ");
							if (sec_arr.length >= 4) {//总长度大于4的才是有效值
								Integer len = Integer.parseInt(sec_arr[1], 16);
								if (len == (sec_arr.length - 3)) {
									switch (Integer.valueOf(sec_arr[0])) {
									case 81:// 车机启动
										zdcCanstream.setIgnition(1);// 发动机点火
										break;
									case 16:// 车速

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
									case 26:// 方向盘信息

										break;
									case 41:// 车身信息
										if (Common.calcHex(sec_arr) == 1) {
											String h_41 = sec_arr[2];
											switch (Integer.valueOf(h_41)) {
											case 01://
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
											case 02:
												Integer data1 = Integer.valueOf(sec_arr[3], 16);
												Integer data2 = Integer.valueOf(sec_arr[4], 16);
												Integer data3 = Integer.valueOf(sec_arr[5], 16);
												Integer data4 = Integer.valueOf(sec_arr[6], 16);
												Integer data5 = Integer.valueOf(sec_arr[7], 16);
												Integer data6 = Integer.valueOf(sec_arr[8], 16);
												Integer data7 = Integer.valueOf(sec_arr[9], 16);
												Integer data8 = Integer.valueOf(sec_arr[10], 16);
												Integer data9 = Integer.valueOf(sec_arr[11], 16);
												Integer data10 = Integer.valueOf(sec_arr[12], 16);
												Integer data11 = Integer.valueOf(sec_arr[13], 16);
												Integer data12 = Integer.valueOf(sec_arr[14], 16);
												zdcCanstream.setEngineSpeed(Double.valueOf(data1 * 256 + data2));// 发动机转速
												zdcCanstream.setInstantaneousVelocity(Double.valueOf((data3 * 256 + data4) * 0.01));// 瞬时速度
												zdcCanstream.setBatteryVoltage(Double.valueOf((data5 * 256 + data6) * 0.01));// 电池电压
												zdcCanstream.setOutsideTemperature(Double.valueOf((data7 * 256 + data8) * 0.1));// 车外温度
												zdcCanstream.setMileage(Double.valueOf(data9 * 65536 + data10 * 256 + data11));// 里程
												zdcCanstream.setResidualOilConsumption(Double.valueOf(data12));// 剩余油耗
												break;
											case 03:
												String b_41_03 = Common.hexString2binaryString(sec_arr[3]);

												if (b_41_03.length() == 8) {
													zdcCanstream.setResidualOilConsumptionState((Integer.valueOf(b_41_03.toString().substring(0, 1))));// 剩余油耗过低报警
													zdcCanstream.setBatteryVoltageState((Integer.valueOf(b_41_03.toString().substring(1, 2))));// 电池电压过低报警
												}
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
						
						// 插入数据库
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = null;
						try {
							date = sdf.parse(sec_arr[sec_arr.length-1].replace("T", " "));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						crt_date= date ;
						zdcCanstream.setUploadTime(new Date());
						zdcCanstream.setDeviceuid(deviceid);
						zdcCanstream.setUploadTime(crt_date);
						zdcCanstreamService.insert(zdcCanstream);
						// 修改已读标记
						zdcCanstreamOriginalService.updateByPrimaryKeySelective(id);
						
					}
				}
			}
		}
	}

	// stream 2E 81 01 FF 002E 41 0D 02 00 00 0D AC 04 B0 00 B4 00 13 88 19 DA2E
	// 41 02 03 00 B9
	public static void main(String[] args) {
		new AutoClacCanStream().autoRun();
	}
}
