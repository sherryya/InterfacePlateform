package com.mina.server.handle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.dto.ZdcAccount;
import com.mina.server.AutoDealUploadData;
import com.mina.server.services.GetRestDataCommon;
public class CanStreamHandle {
	private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
	AutoDealUploadData autoDealUploadData = new AutoDealUploadData();
	String key = "";
	String b;
	/** 车速 */
	StringBuffer bufferChesu = new StringBuffer();
	/** 空调信息 */
	StringBuffer bufferKongtiao = new StringBuffer();
	/** 基本信息 */
	StringBuffer bufferJiben = new StringBuffer();
	/** 车身信息 */
	StringBuffer bufferCheshen = new StringBuffer();
	/** 故障码信息 */
	StringBuffer bufferGuzhangma = new StringBuffer();
	/** 清除故障码信息 */
	StringBuffer bufferClearGuzhangma = new StringBuffer();
	/** OBD数据流 **/
	StringBuffer bufferOBDStream = new StringBuffer();
	/** 中控锁信息 */
	StringBuffer bufferZhongkongsuo = new StringBuffer();
	/** 车门 */
	StringBuffer bufferChemen1 = new StringBuffer();
	/** 车窗 */
	StringBuffer bufferChechuang1 = new StringBuffer();
	/** 熄火或打火 */
	StringBuffer bufferifxihuo = new StringBuffer();
	/** 总线长度 */
	StringBuffer bufferzhongxian = new StringBuffer();
	
	/** 车辆状态 */
	StringBuffer bufferStatus = new StringBuffer();
	
	/** 发送 */
	StringBuffer bufferSend = new StringBuffer();
	StringBuffer buffer = new StringBuffer();
	ArrayList<String> al = new ArrayList<String>();
	String can_temp = "";
	String can = "";
	String terminal_id = "";
	public int handleCanStream(String canstream,String terminal_id) {
		can=canstream;
		this.terminal_id=terminal_id;
		try {
			if (can != null) {
				if (can.length() > 9) {
					    can_temp=can;
						logger.info("############canStream:" + can_temp.toUpperCase());
						al = new ArrayList<String>();
						String l = can_temp.toUpperCase();
						if (l.length() > 9)
							while (true)// 把得到的全部指令解析到list中 有不符合格式的CAN 数据就仍掉
							{
								System.out.println("############解析前:" + l);
								String len = "";
								if (l.length() <= 9) {// 长度小于9说明指令不合法
									logger.info("############指令长度不合法:"+l);
									break;
								} else {
									if (l.substring(0, 2).equalsIgnoreCase("2E"))// 如果不是以2E开头的直接扔掉
									{
										len = l.substring(4, 6);// 得到指令的长度
									}
									else
									{
										logger.info("############指令不合法:"+l);
										break;
									}
								}
								Integer sp1 = Integer.valueOf(len, 16);
								if (l.length() < (4 + 2 + sp1 * 2 + 2)) {// 判断指令格式是否正确
									//continue;
									logger.info("############指令不合法:"+l);
									break;
								} else {
									String l_temp = l.substring(0, 4 + 2 + sp1 * 2 + 2);// 得到单个正确的指令
									al.add(l_temp);
									l = l.replace(l_temp, "");
									if (l.length() == 0) {
										break;
									}
								}
							}
					for (String a : al) {
						System.out.println("############解析后:" + a);
					}
					if (!al.isEmpty()) {
						 analyseOrder();
					}
					logger.info("############analyse over:" + can.toUpperCase());
				}
				else
				{
					logger.info("############指令长度不合法:"+can);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("TTLAutoSendCanOrderService 发生异常：" + ex.getMessage() + "can:" + can);
		}
		return 1;
	}
	private void analyseOrder()
	{
		 bufferChesu = new StringBuffer();
		/** 空调信息 */
		 bufferKongtiao = new StringBuffer();
		/** 基本信息 */
		 bufferJiben = new StringBuffer();
		/** 车身信息 */
		 bufferCheshen = new StringBuffer();
		/** 故障码信息 */
		 bufferGuzhangma = new StringBuffer();
		/** 清除故障码信息 */
		 bufferClearGuzhangma = new StringBuffer();
		 /**OBD数据流**/
		 bufferOBDStream=new StringBuffer();
		/** 中控锁信息 */
		 bufferZhongkongsuo = new StringBuffer();
		/** 车门 */
		 bufferChemen1 = new StringBuffer();
		/** 车窗 */
		 bufferChechuang1 = new StringBuffer();
		/** 熄火或打火 */
		 bufferifxihuo = new StringBuffer();
		 bufferStatus = new StringBuffer();
		/** 总线长度 */
		 bufferzhongxian = new StringBuffer();
		/** 发送 */
		 bufferSend = new StringBuffer();
		 buffer=new StringBuffer();
	   for(int i=0;i<al.size();i++)
	   {
		   String can_type=al.get(i).toString().substring(2, 4);
		   int type2=0;// 02  自动发送， 01  还是手动触发的
		   type2=Integer.valueOf(al.get(i).toString().substring(6, 8));// 判断是自动触发，还是手动触发   02：自动  01:手动

/*		   if(can_type.equalsIgnoreCase("24"))//基本信息
		   {
			   if (type2 == 2)
			   {
				   buffer.append(Util.getRigthOrder(al.get(i)));
				   buffer.append("#");
			   }
		   }*/
/*	       else if(can_type.equalsIgnoreCase("71"))//总线数据   总线数据特殊，长度占两个字节，需要特殊处理
		   {
			   buffer.append(al.get(i));
			   buffer.append("#");
		   }*/
		   if(can_type.equalsIgnoreCase("41"))//车身信息
		   {
			int type1 = Integer.valueOf(al.get(i).toString().substring(8, 10));// 41的基本类型 01 02 03 04 05
			switch (type1) {
			case 01: //车门 需要判断是自动发送的，还是手机触发的
				if (type2 == 2) {
					//buffer.append(Util.getRigthOrder(al.get(i)));
					//buffer.append("#");
				} else {
					key = "chemen";
					bufferChemen1.append(Util.getRigthOrder(al.get(i)));
				}
				break;
			case 02: // 转速等 直接入库
				if (type2 == 2) {
					buffer.append(Util.getRigthOrder(al.get(i)));
					buffer.append("#");
				} else {
					logger.info("###############车辆当前状态###################:" + al.get(i));
					key = "status";
					bufferStatus.append(Util.getRigthOrder(al.get(i)));
					logger.info("###############车辆当前状态###################:" + bufferStatus);
				}
				break;
			case 03: // 剩余油量等 直接入库
				if (type2 == 2) {
					logger.info("###############剩余油量等 直接入库###################:"+al.get(i));
				 //buffer.append(Util.getRigthOrder(al.get(i)));
				// buffer.append("#");
				}
				break;
			case 04: //  车窗开度  需要判断是自动发送的，还是手机触发的
				 type2=Integer.valueOf(al.get(i).toString().substring(6, 8));//判断是自动触发，还是手动触发  02:自动  01:手动
				if (type2 == 2) {
					//buffer.append(Util.getRigthOrder(al.get(i)));//车窗开度没有自动发送
					//buffer.append("#");
				} else {
					key = "chechuang";
					bufferChechuang1.append(Util.getRigthOrder(al.get(i)));
				}
				break;
			case 05: // 车辆启动
				String rightOrder=Util.getRigthOrder(al.get(i));
				 bufferifxihuo.append(rightOrder);//为了处理故障码用的
				break;
			case 06://OBD 数据流   只所有需要保存到全局变量里，是因为有可能一次无法全部接收完成   需要等待接收完，再做处理
				break;
			case 07://综合 41中的(01 02 03 04) 以及24 的全部功能
				if (type2 == 2) {
					buffer.append(Util.getRigthOrder(al.get(i)));
					buffer.append("#");
				} else {
					logger.info("###############车辆当前状态###################:" + al.get(i));
					key = "status";
					bufferStatus.append(Util.getRigthOrder(al.get(i)));
					logger.info("###############车辆当前状态###################:" + bufferStatus);
				}
				break;
			default:
				break;
			}
		   } 
		   else if(can_type.equalsIgnoreCase("A1"))//中控锁状态
		   {
			   bufferZhongkongsuo.append(Util.getRigthOrder(al.get(i)));
		   }
		   else if(can_type.equalsIgnoreCase("51"))// 故障码
		   {
			   String order_code=Util.getRigthOrder(al.get(i));
			   if (Util.getFaultCode(order_code)) {
			   bufferGuzhangma.append(order_code+"#");
			   }
		   }
		   else if(can_type.equalsIgnoreCase("61"))// 清除故障码
		   {
			   bufferClearGuzhangma.append(Util.getRigthOrder(al.get(i)));
		   }
	   }		
	   if (buffer.length()>0) {
		   save_can_platform(buffer.toString());//保存CAN数据到平台
	   }
		if (bufferGuzhangma.toString().length()>0) {//保存故障码信息到平台
			save_faultcode_platform(bufferGuzhangma);
		}
		if (bufferifxihuo.toString().length() > 0) {// 打火或熄火
			save_can_platformForStart(bufferifxihuo + "#");// 单独保存启动，和熄火标记到服务器
			String tel = getTel(terminal_id);
			String strType = "";
			if (MyConstant.CAR_START_STOP_REC.equalsIgnoreCase(bufferifxihuo.toString())) {
				strType = "0";
			} else if (MyConstant.CAR_START_REC.equalsIgnoreCase(bufferifxihuo.toString())) {
				strType = "1";
			} else {
				strType = "-1";
			}
			sendToTel("CAR", tel, terminal_id, strType, "32");
			bufferifxihuo = null;
		}
		if(bufferOBDStream.toString().length()==0)//OBD数据流
		{
			//save_obdStream_platform();
		}
		if (bufferZhongkongsuo.length() > 0) {//中控锁
			String tel=getTel(terminal_id);
			sendToTel("CAR",tel,terminal_id,bufferZhongkongsuo.toString(),"14");
			bufferZhongkongsuo=null;
		}
		if (key.equals("chemen")) {// 车门信息
			if (bufferChemen1.toString().length() > 0) {// 车门
				String tel=getTel(terminal_id);
				sendToTel("CAR",tel,terminal_id,bufferChemen1.toString(),"10");
				key="";
				bufferChemen1=null;
			}
		}
		
		if (key.equals("chechuang")) {// 车窗信息
			if (bufferChechuang1.toString().length() > 0) {// 车窗
				String tel=getTel(terminal_id);
				sendToTel("CAR",tel,terminal_id,bufferChechuang1.toString(),"12");
				key="";
				bufferChechuang1=null;
			}
		}
		
		if (bufferClearGuzhangma.length() > 0) {//清除故障码
			String tel=getTel(terminal_id);
			sendToTel("CAR",tel,terminal_id,bufferClearGuzhangma.toString(),getSendType(bufferClearGuzhangma.toString()));
			bufferClearGuzhangma=null;
		}
		
		if (key.equals("status")) {// 车辆状态 点火还是熄火
			logger.info("###############车辆当前状态#######key:"+key);
			logger.info("###############车辆当前状态#######key:"+key+"############:"+bufferStatus.toString());
			if (bufferStatus.length() > 0) {
				logger.info("###############车辆当前状态###################:"+bufferStatus.toString());
				String tel = getTel(terminal_id);
				sendToTel("CAR", tel, terminal_id, bufferStatus.toString(), "34");
				bufferStatus = null;
				key="";
			}
		}
   }
	/**
	 * 保存CAN流到平台
	 * @param buffer
	 */
	private void save_can_platform(String buffer) {
		// 保存CAN流到平台
		if (buffer.toString().split("#").length >= 1) // 如果结果中没有启动或停止的状态，那么需要长度为>=4的时候才可以入库，为了保证数据的完整性
		{
			if (Util.ordersVAL(buffer.toString())) {
				bufferSend.append(buffer + Util.getCurrDateTime() + ";");
				autoDealUploadData.DealUploadData_CAN(bufferSend.toString(),terminal_id);
			}
		}
	}
	
	private void save_can_platformForStart(String buffer) {
		// 保存CAN流到平台
		if (buffer.toString().split("#").length >= 1) // 如果结果中没有启动或停止的状态，那么需要长度为>=4的时候才可以入库，为了保证数据的完整性
		{
			if (Util.ordersVAL(buffer.toString())) {
				bufferSend.append(buffer + Util.getCurrDateTime() + ";");
				autoDealUploadData.Upd_Terminal_Onlineflag(bufferSend.toString(),terminal_id);
			}
		}
	}
	/**
	 * 保存故障码信息到平台
	 * @param bufferGuzhangma
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void save_faultcode_platform(StringBuffer bufferGuzhangma) {

		bufferGuzhangma.append(bufferGuzhangma + Util.getCurrDateTime() + ";");
		Map paramMap = new HashMap();
		paramMap.put("deviceuid", terminal_id);
		paramMap.put("fault_code", bufferGuzhangma.toString()); 
		try {
			new GetRestDataCommon().getRestDataCommon(paramMap, "FaultOriginal.insert");
		} catch (Exception e) {
			e.printStackTrace();
		}
		bufferGuzhangma = null;
	}
	/**
	 * 将信息推送到手机端展示
	 * @param sendTerminal
	 * @param Tel
	 * @param IMEI
	 * @param sendType
	 * @param Msgtype
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	private void sendToTel(String sendTerminal,String Tel,String IMEI,String sendType,String Msgtype)
	{	
		Map paramMap = new HashMap();
		paramMap.put("sendTerminal", sendTerminal);// 发送终端
		paramMap.put("Tel", Tel);// 手机
		paramMap.put("IMEI", IMEI);// 终端
		paramMap.put("sendType", sendType);// 发送类型
		paramMap.put("Msgtype", Msgtype);// 信息类型
		try {
			new GetRestDataCommon().getRestDataCommon(paramMap, "sendOperInstructController.send");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 得到故障码类别
	 * @param temp
	 * @return
	 */
    private String getSendType(String temp)
    {
    	//2E610200009C
    	String type="";
    	String ret="";
    	if(temp.trim().length()==12)
    	{
    		type=temp.substring(6, 8);
    		switch (Integer.valueOf(type)) {
				case 00:
					ret="18";
					break;
				case 01:
					ret="20";
					break;
				case 02:
					ret="22";
					break;
				case 03:
					ret="24";
					break;
				case 04:
					ret="26";
					break;
				case 05:
					ret="28";
				     break;
				default:
				break;
			}
    	}
    	return ret;
    }
    /**
     * 通过设备ID得到手机  用于推送
     * @param terminal
     * @return
     */
    public String getTel(String terminal)
    {
    	logger.info("############终端ID:"+terminal);
    	ZdcAccount zdc=new ZdcAccount();
    	zdc=new AutoDealUploadData().getAccountNameByTerminalID(terminal);
    	if(zdc!=null){
    		return zdc.getAccount_name();
    	}
    	else
    	{
    		return "";
    	}
    }
}
