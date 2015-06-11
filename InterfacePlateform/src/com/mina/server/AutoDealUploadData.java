package com.mina.server;
import java.util.Date;

import org.springframework.context.ApplicationContext;

import com.db.dto.TZdcTerminalOnlineflag;
import com.db.dto.ZdcAccount;
import com.db.dto.ZdcCanstream;
import com.db.dto.ZdcCanstreamOriginal;
import com.db.dto.ZdcGpsinfo;
import com.db.dto.ZdcGpsinfoOriginal;
import com.db.service.TZdcDataitemService;
import com.db.service.TZdcTerminalOnlineflagService;
import com.db.service.ZdcAccountService;
import com.db.service.ZdcCanstreamOriginalService;
import com.db.service.ZdcCanstreamService;
import com.db.service.ZdcGpsinfoOriginalService;
import com.db.service.ZdcGpsinfoService;
import com.system.SpringApplicationContextFactory;
public class AutoDealUploadData {
	private static ApplicationContext ctx = SpringApplicationContextFactory.newInstance();
	ZdcGpsinfo zdcGpsinfo = new ZdcGpsinfo();
	ZdcGpsinfoService zdcGpsinfoService;
	ZdcGpsinfoOriginalService zdcGpsinfoOrigService;
	ZdcCanstream  zdcCanstream=new ZdcCanstream();
	ZdcCanstreamService  zdcCanstreamService;
	TZdcTerminalOnlineflagService tZdcTerminalOnlineflagService;
	ZdcCanstreamOriginal  zdcCanstreamOriginal=new ZdcCanstreamOriginal();
	ZdcCanstreamOriginalService  zdcCanstreamOriginalService;
	
	ZdcAccountService zdcAccountService;
	public AutoDealUploadData()
	{
		
		
	}
	/**
	 * GPS规则   lat（纬度），lon（精度），height（高度），speed（速度），bear(方向)，hdop（精度）,time（上传时间）,deviceid（设备ID）
	 * 
	 * 
	 * @param stream
	 * @return
	 */
	public int DealUploadData_GPS(String stream,String deviceid) {
		zdcGpsinfoOrigService = ctx.getBean(ZdcGpsinfoOriginalService.class);
		ZdcGpsinfoOriginal zdcGpsinfoOraginal = new ZdcGpsinfoOriginal();
		zdcGpsinfoOraginal.setDeviceid(deviceid);
		zdcGpsinfoOraginal.setStream(stream);
		//插入数据到t_zdc_gpsinfo_original
		int count = zdcGpsinfoOrigService.insert(zdcGpsinfoOraginal);
		return count;
		
	}
	/**   CAN数据未解析 直接存储
	 * 2E 81 01 FF 00  点火
	 * 2E 81 00 FF 00  熄火
	 * 2E 16 02 00 00 E7 车速
	 * 2E 24 02 06 0B C8 基本信息
	 * 2E 26 02 00 00 D7 方向盘转角
	 * 2E 41 02 01 AF 0C   车身信息
	 * 2E 41 0D 02 00 00 0D AC 04 B0 00 B4 00 13 88 19 DA   车身信息
	 * 2E 41 02 03 00 B9   车身信息
	 * @param stream
	 * @return
	 */
	public int DealUploadData_CAN(String stream,String deviceid) {
		try {
			zdcCanstreamOriginalService = ctx.getBean(ZdcCanstreamOriginalService.class);
			zdcCanstreamOriginal.setStream(stream);
			zdcCanstreamOriginal.setDeviceid(deviceid);
			zdcCanstreamOriginalService.insert(zdcCanstreamOriginal);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
   /**
    * 通过设备ID得到手机  用于推送
    * @param account_name
    * @return
    */
	public ZdcAccount getAccountNameByTerminalID(String account_name) {
		try {
			zdcAccountService = ctx.getBean(ZdcAccountService.class);
			return zdcAccountService.getAccountNameByTerminalID(account_name);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	
	
	public int Upd_Terminal_Onlineflag(String stream,String deviceid) {
		int ret=DealUploadData_CAN(stream,deviceid);
		if(ret>0)
		{
			String  arr=stream.split("#")[0];
			TZdcTerminalOnlineflag tZdcTerminalOnlineflag=new TZdcTerminalOnlineflag();
			tZdcTerminalOnlineflag.setTerminalImei(deviceid);
			if(arr.equalsIgnoreCase("2E41020500B7"))//stop
			{
				tZdcTerminalOnlineflag.setOnlimneStatus("0");
			}
			else
			{
				tZdcTerminalOnlineflag.setOnlimneStatus("1");
			}
			tZdcTerminalOnlineflag.setUpdateDate(new Date());
			tZdcTerminalOnlineflagService = ctx.getBean(TZdcTerminalOnlineflagService.class);
			//查看车机是否在线
			TZdcTerminalOnlineflag terminalOnlineFlag1 = tZdcTerminalOnlineflagService.selectInfoByImei(deviceid);
			//如果在线则下发短信
			if(null !=terminalOnlineFlag1)
			{
				tZdcTerminalOnlineflagService.update(tZdcTerminalOnlineflag);
			}
			else
			{
				tZdcTerminalOnlineflagService.insert(tZdcTerminalOnlineflag);
			}
		}
		return 0;
	}
	public static void main(String[] args) {
	
		String ret="";
		 new AutoDealUploadData().Upd_Terminal_Onlineflag("111111111111#333","111111111111");
		System.out.println(ret);
	}
}
