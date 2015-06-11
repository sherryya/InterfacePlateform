package com.crontab.util;

import java.io.FileWriter;
import java.util.List;

import org.apache.log4j.Logger;

import com.crontab.CronAutoClacCanStream;
import com.db.dto.ZdcCanstream;
import com.db.dto.ZdcGpsinfo;

public class FileUtil {

	private static final Logger log = Logger.getLogger(CronAutoClacCanStream.class.getName());
	
	/**
	 * 将CAN数据写入文件，
	 * @param path_name
	 * @param zdcCanstream
	 * @return
	 */
	public  boolean canstream_writeFileForDB(String path_name,List<ZdcCanstream> zdcCanstream)
	{
		FileWriter fw = null;
		try {
			String str_path=new SysPathUtil().getPath("can")+path_name+".txt";
			log.info("CAN数据流 存储文件路径:"+str_path);
			fw = new FileWriter(str_path);
			//long begin3 = System.currentTimeMillis();
			StringBuffer sb=null;
			for(ZdcCanstream stream:zdcCanstream)
			{
				sb=new StringBuffer();
				sb.append(" ");//自增长ID必须有占位符
				sb.append(",");
				sb.append(stream.getDeviceuid());
				sb.append(",");
				sb.append(stream.getIgnition());
				sb.append(",");
				sb.append(stream.getReversing());
				sb.append(",");
				sb.append(stream.getBrake());
				sb.append(",");
				sb.append(stream.getLight());
				sb.append(",");
				sb.append(stream.getLeftBeforeDoor());
				sb.append(",");
				sb.append(stream.getRigthBeforeDoor());
				sb.append(",");
				sb.append(stream.getLeftAfterDoor());
				sb.append(",");
				sb.append(stream.getRightAfterDoor());
				sb.append(",");
				sb.append(stream.getTailBoxDoor());
				sb.append(",");
				sb.append(stream.getBefortCover());
				sb.append(",");
				sb.append(stream.getSteeringWheelAngle());
				sb.append(",");
				sb.append(stream.getCleaningLiquid());
				sb.append(",");
				sb.append(stream.getSafetyBelt());
				sb.append(",");
				sb.append(stream.getEngineSpeed());
				sb.append(",");
				sb.append(stream.getInstantaneousVelocity());
				sb.append(",");
				sb.append(stream.getSpeed());
				sb.append(",");
				sb.append(stream.getBatteryVoltage());
				sb.append(",");
				sb.append(stream.getOutsideTemperature());
				sb.append(",");
				sb.append(stream.getMileage());
				sb.append(",");
				sb.append(stream.getResidualOilConsumption());
				sb.append(",");
				sb.append(stream.getResidualOilConsumptionState());
				sb.append(",");
				sb.append(stream.getBatteryVoltageState());
				sb.append(",");
				sb.append(stream.getMileageFinish());
				sb.append(",");
				sb.append("");//FF25000001
				sb.append(",");
				sb.append("");//FF25000002
				sb.append(",");
				sb.append("");//Left_Before_Window
				sb.append(",");
				sb.append("");//Rigth_Before_Window
				sb.append(",");
				sb.append("");//Left_After_Window
				sb.append(",");
				sb.append("");//Right_After_Window
				sb.append(",");
				sb.append("");//Left_Before_PSI
				sb.append(",");
				sb.append("");//Rigth_Before_PSI
				sb.append(",");
				sb.append("");//Left_After_PSI
				sb.append(",");
				sb.append("");//Right_After_PSI
				sb.append(",");
				sb.append(stream.getStr_uploadTime());
				sb.append(",1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");
				fw.write(sb.toString()+"\r\n");   
			}
			fw.close();
			//long end3 = System.currentTimeMillis();
			//System.out.println("FileWriter执行耗时:" + (end3 - begin3) + "豪秒");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	/**
	 * 将gps数据写入文件，
	 * @param path_name
	 * @param zdcCanstream
	 * @return
	 */
	public  boolean gpsstream_writeFileForDB(String path_name,List<ZdcGpsinfo> gpsstream)
	{
		FileWriter fw = null;
		try {
			String str_path=new SysPathUtil().getPath("gps")+path_name+".txt";
			log.info("gps数据流 存储文件路径:"+str_path);
			fw = new FileWriter(str_path);
			//long begin3 = System.currentTimeMillis();
			StringBuffer sb=null;
			for(ZdcGpsinfo stream:gpsstream)
			{
				sb=new StringBuffer();
				sb.append(" ");//自增长ID必须有占位符
				sb.append(",");
				sb.append(stream.getDeviceuid());
				sb.append(",");
				sb.append(stream.getGpstime());
				sb.append(",");
				sb.append(stream.getLatitude());
				sb.append(",");
				sb.append(stream.getLongitude());
				sb.append(",");
				sb.append(stream.getHeight());
				sb.append(",");
				sb.append(stream.getSpeed());
				sb.append(",");
				sb.append(0);
				sb.append(",");
				sb.append(stream.getDirection());
				sb.append(",");
				sb.append(0);
				sb.append(",");
				sb.append("E8");
				sb.append(",");
				sb.append("100");
				sb.append(",");
				sb.append(1);
				sb.append(",");
				sb.append(stream.getHdop());
				sb.append(",");
				sb.append(0);
				fw.write(sb.toString()+"\r\n");   
			}
			fw.close();
			//long end3 = System.currentTimeMillis();
			//System.out.println("FileWriter执行耗时:" + (end3 - begin3) + "豪秒");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}
