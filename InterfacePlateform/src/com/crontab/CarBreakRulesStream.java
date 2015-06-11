package com.crontab;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;

import com.crontab.util.BreakPlatUtil;
import com.db.dto.TItovCarVo;
import com.db.dto.TZdcBreakruleqryFlag;
import com.db.dto.TZdcBreakrules;
import com.db.dto.TZdcBreakrulescitylist;
import com.db.service.TzdcBreakRulesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.SpringApplicationContextFactory;
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CarBreakRulesStream implements Job {
	private static final Logger log = Logger.getLogger(CarBreakRulesStream.class.getName());
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

		// TODO Auto-generated method stub
		//long account_id = ParamData.getLong(requestParam.getBodyNode(), "account_id");
		//查询全部车辆信息
		List<TItovCarVo> carList = zdcBreakRuleService.qryCarInfoAll();
		
		for(TItovCarVo carInfo:carList)
		{
			if(null != carInfo.getAccountId()&&!"".equals(carInfo.getAccountId()))
			{
				TZdcBreakruleqryFlag carvo = zdcBreakRuleService.qryBreak(carInfo.getCarPlateNumber());
				SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
				if(carvo!=null)
				{
					String updateTime = carvo.getUpdatetime();
						try {
							long time1 = sdf.parse(updateTime).getTime();
							long time2 = sdf.parse(sdf.format(new Date())).getTime();
							//表示此车牌号当天没有被查询过插入完成后更新车牌查询的时间
							if(time1<time2)
							{
								TZdcBreakrulescitylist breakCity = new TZdcBreakrulescitylist();
								
								if(StringUtils.isNotBlank(carInfo.getCity_code()))
								{
									breakCity.setCityCode(carInfo.getCity_code());
								}
								//根据省份和城市名称获取是否需要发动机参数或者车架号蚕食
								List<TZdcBreakrulescitylist> cityList = zdcBreakRuleService.selectByPcodeCity(breakCity);
								if(null !=cityList&&cityList.size()>0)
								{
									TZdcBreakrulescitylist breakCityList=  cityList.get(0);
									String engineno = "";  //发动机号
									String classno = "";   //车架号
									if(null !=breakCityList)
									{
										
										int engine = breakCityList.getEngine();  //是否需要发动机
										int carnume = breakCityList.getClasst();   //是否需要车架号
										int engineno1 = breakCityList.getEngineno();  //需要几位发动机号
										int classno1 = breakCityList.getClassno();  //需要几位车架号
										if(engine==1)
										{
											if(engineno1==0)
											{
												engineno = carInfo.getCar_engine_num();
												
											}else
											{
												engineno = carInfo.getCar_engine_num().substring(carInfo.getCar_engine_num().length()-engineno1, carInfo.getCar_engine_num().length());
											}
											
										}
										if(carnume ==1)
										{
											if(classno1==0)
											{
												classno = carInfo.getCar_carcase_num();
											}
											else
											{
												classno = carInfo.getCar_carcase_num().substring(carInfo.getCar_carcase_num().length()-classno1, carInfo.getCar_carcase_num().length());
											}
											
										}
										
									}
									
									//调用api
									String jsonResult = BreakPlatUtil.getInfo(carInfo.getCity_code(),engineno, classno, carInfo.getCarPlateNumber());
									ObjectMapper objectMapper = new ObjectMapper();
									if (StringUtils.isNotEmpty(jsonResult)) {
										JsonNode jsonNode;
										try {
											jsonNode = objectMapper.readTree(jsonResult);
										if ("200".equals(jsonNode.get("resultcode").textValue())) {
											JsonNode jsonNodeResult = jsonNode.get("result");
											if (jsonNodeResult != null) {
												String provincet = jsonNodeResult.get("province").textValue();
												String city = jsonNodeResult.get("city").textValue();
												String hphmt =  jsonNodeResult.get("hphm").asText();
												JsonNode jsonNodeList= jsonNodeResult.get("lists");

												for (int i = 0; i < jsonNodeList.size(); i++) {
													JsonNode jsonList = jsonNodeList.get(i);
													String date = jsonList.get("date").textValue();
													String area = jsonList.get("area").textValue();
													String act = jsonList.get("act").textValue();
													String code = jsonList.get("code").textValue();
													String fen = jsonList.get("fen").textValue();
													String money = jsonList.get("money").textValue();
													String handled = jsonList.get("handled").textValue();
													
													TZdcBreakrules tzdcBreakRules = new TZdcBreakrules();
													tzdcBreakRules.setAct(act);
													tzdcBreakRules.setArea(area);
													tzdcBreakRules.setCity(city);
													tzdcBreakRules.setCode(code);
													tzdcBreakRules.setDate(date);
													tzdcBreakRules.setFen(fen);
													tzdcBreakRules.setHandled(handled);
													tzdcBreakRules.setHphm(hphmt);
													tzdcBreakRules.setMoney(money);
													tzdcBreakRules.setProvince(provincet);
													//根据车牌号和时间查询是否存在此违章信息如果存在则不插入如果不存在则插入
													int num = zdcBreakRuleService.isExistBreakRule(tzdcBreakRules);
													if(num==0)
													{
														try{
															//插入违章信息
															int count = zdcBreakRuleService.insert(tzdcBreakRules);
															
														}catch(Exception e)
														{
															log.error("车辆违章信息插入失败"+hphmt+date);
															
														}
													}
													
													
												}
											
											} else {
												log.error("得不到json返回值");
											}

										} else {
											log.error(jsonNode.get("reason") );
										}
										
									} catch (JsonProcessingException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
									
								
								
							}
								//已经查询过则更新此车牌查询的信息
								TZdcBreakruleqryFlag breakQryFlag = new TZdcBreakruleqryFlag();
								breakQryFlag.setCarPalteNum(carInfo.getCarPlateNumber());
								breakQryFlag.setUpdatetime(sdf.format(new Date()));
								zdcBreakRuleService.updateQryRecord(breakQryFlag);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				}else
				{

					TZdcBreakrulescitylist breakCity = new TZdcBreakrulescitylist();
					
					if(StringUtils.isNotBlank(carInfo.getCity_code()))
					{
						breakCity.setCityCode(carInfo.getCity_code());
					}
					//根据省份和城市名称获取是否需要发动机参数或者车架号蚕食
					List<TZdcBreakrulescitylist> cityList = zdcBreakRuleService.selectByPcodeCity(breakCity);
					if(null !=cityList&&cityList.size()>0)
					{
						TZdcBreakrulescitylist breakCityList=  cityList.get(0);
						String engineno = "";  //发动机号
						String classno = "";   //车架号
						if(null !=breakCityList)
						{
							
							int engine = breakCityList.getEngine();  //是否需要发动机
							int carnume = breakCityList.getClasst();   //是否需要车架号
							int engineno1 = breakCityList.getEngineno();  //需要几位发动机号
							int classno1 = breakCityList.getClassno();  //需要几位车架号
							if(engine==1)
							{
								if(engineno1==0)
								{
									engineno = carInfo.getCar_engine_num();
									
								}else
								{
									engineno = carInfo.getCar_engine_num().substring(carInfo.getCar_engine_num().length()-engineno1, carInfo.getCar_engine_num().length());
								}
								
							}
							if(carnume ==1)
							{
								if(classno1==0)
								{
									classno = carInfo.getCar_carcase_num();
								}
								else
								{
									classno = carInfo.getCar_carcase_num().substring(carInfo.getCar_carcase_num().length()-classno1, carInfo.getCar_carcase_num().length());
								}
								
							}
							
						}
						
						//调用api
						String jsonResult = BreakPlatUtil.getInfo(carInfo.getCity_code(),engineno, classno, carInfo.getCarPlateNumber());
						ObjectMapper objectMapper = new ObjectMapper();
						if (StringUtils.isNotEmpty(jsonResult)) {
							JsonNode jsonNode;
							try {
								jsonNode = objectMapper.readTree(jsonResult);
							if ("200".equals(jsonNode.get("resultcode").textValue())) {
								JsonNode jsonNodeResult = jsonNode.get("result");
								if (jsonNodeResult != null) {
									String provincet = jsonNodeResult.get("province").textValue();
									String city = jsonNodeResult.get("city").textValue();
									String hphmt =  jsonNodeResult.get("hphm").asText();
									JsonNode jsonNodeList= jsonNodeResult.get("lists");

									for (int i = 0; i < jsonNodeList.size(); i++) {
										JsonNode jsonList = jsonNodeList.get(i);
										String date = jsonList.get("date").textValue();
										String area = jsonList.get("area").textValue();
										String act = jsonList.get("act").textValue();
										String code = jsonList.get("code").textValue();
										String fen = jsonList.get("fen").textValue();
										String money = jsonList.get("money").textValue();
										String handled = jsonList.get("handled").textValue();
										
										TZdcBreakrules tzdcBreakRules = new TZdcBreakrules();
										tzdcBreakRules.setAct(act);
										tzdcBreakRules.setArea(area);
										tzdcBreakRules.setCity(city);
										tzdcBreakRules.setCode(code);
										tzdcBreakRules.setDate(date);
										tzdcBreakRules.setFen(fen);
										tzdcBreakRules.setHandled(handled);
										tzdcBreakRules.setHphm(hphmt);
										tzdcBreakRules.setMoney(money);
										tzdcBreakRules.setProvince(provincet);
										//根据车牌号和时间查询是否存在此违章信息如果存在则不插入如果不存在则插入
										int num = zdcBreakRuleService.isExistBreakRule(tzdcBreakRules);
										if(num==0)
										{
											try{
												//插入违章信息
												int count = zdcBreakRuleService.insert(tzdcBreakRules);
												
											}catch(Exception e)
											{
												log.error("车辆违章信息插入失败"+hphmt+date);
												
											}
										}
										
										
									}
								
								} else {
									log.error("得不到json返回值");
								}

							} else {
								log.error(jsonNode.get("reason") );
							}
							
						} catch (JsonProcessingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
						
					
					
				}
					//已经查询过则更新此车牌查询的信息
					TZdcBreakruleqryFlag breakQryFlag = new TZdcBreakruleqryFlag();
					breakQryFlag.setCarPalteNum(carInfo.getCarPlateNumber());
					breakQryFlag.setUpdatetime(sdf.format(new Date()));
					zdcBreakRuleService.insertqryBreak(breakQryFlag);
				}
				
		}
	  
		}
		
	}


}
