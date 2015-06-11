package com.jpush.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpush.JPushClient;
import com.jpush.common.DeviceEnum;
import com.jpush.push.CustomMessageParams;
import com.jpush.push.MessageResult;
import com.jpush.push.ReceiverTypeEnum;
import com.jpush.report.ReceivedsResult;

public class JPushClientExample {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushClientExample.class);

    // demo App defined in resources/jpush-api.conf 
/*	private static final String appKey ="00eb7eadee4c8d07d2463da2";
	private static final String masterSecret = "46bad9c2951c239a8b8f67fe";*/
	private static final String appKey ="789d954d5e4780786a1091b6";
	private static final String masterSecret = "fab232573e869dfd45e6bde0";
	public static final String msgTitle = "Test from API example";
    public static final String msgContent = "Test111";
    public static final String registrationID = "0a0faadea1d"; 
    public static final String tag = "18646117093";

	public static void main(String[] args) {
		//testSend();
		//SendMsgRegistrationID("dd", "{\"body\":{\"Latitude\":\"126.679576\",\"Longitude\":\"45.743820\",\"Msgtype\":\"1\",\"Title\":\"下发音乐\",\"Imageurl\":\"\",\"Noticeurl\":\"\",\"Content\":\"\",\"strType\":\"0\",\"strNum\":\"\"}}");
		SendMsg("18646117093","dd", "{\"body\":{\"Latitude\":\"126.679576\",\"Longitude\":\"45.743820\",\"Msgtype\":\"1\",\"Title\":\"下发音乐\",\"Imageurl\":\"\",\"Noticeurl\":\"\",\"Content\":\"\",\"strType\":\"0\",\"strNum\":\"\"}}");
		//testGetReport();
	}

	private static void testSend() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 0, DeviceEnum.Android, false);
		CustomMessageParams params = new CustomMessageParams();
		//params.setReceiverType(ReceiverTypeEnum.REGISTRATION_ID);
		//params.setReceiverValue(registrationID);
		params.setReceiverType(ReceiverTypeEnum.TAG);
		params.setReceiverValue(tag);
		
		MessageResult msgResult = jpushClient.sendCustomMessage(msgTitle, msgContent, params, null);
        LOG.debug("responseContent - " + msgResult.responseResult.responseContent);
		if (msgResult.isResultOK()) {
	        LOG.info("msgResult - " + msgResult);
	        LOG.info("messageId - " + msgResult.getMessageId());
		} else {
		    if (msgResult.getErrorCode() > 0) {
		        // 业务异常
		        LOG.warn("Service error - ErrorCode: "
		                + msgResult.getErrorCode() + ", ErrorMessage: "
		                + msgResult.getErrorMessage());
		    } else {
		        // 未到达 JPush 
		        LOG.error("Other excepitons - "
		                + msgResult.responseResult.exceptionString);
		    }
		}
	}
	
	public static void testGetReport() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		ReceivedsResult receivedsResult = jpushClient.getReportReceiveds("1708010723,1774452771");
        LOG.debug("responseContent - " + receivedsResult.responseResult.responseContent);
		if (receivedsResult.isResultOK()) {
		    LOG.info("Receiveds - " + receivedsResult);
		} else {
            if (receivedsResult.getErrorCode() > 0) {
                // 业务异常
                LOG.warn("Service error - ErrorCode: "
                        + receivedsResult.getErrorCode() + ", ErrorMessage: "
                        + receivedsResult.getErrorMessage());
            } else {
                // 未到达 JPush
                LOG.error("Other excepitons - "
                        + receivedsResult.responseResult.exceptionString);
            }
		}
	}
	/**
	 * 推送客户端信息
	 * @param tag
	 * @param msgTitle
	 * @param msgContent
	 * @return
	 */
	public static String SendMsg(String tag,String msgTitle,String msgContent) {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 0, DeviceEnum.Android, false);
		CustomMessageParams params = new CustomMessageParams();
		//params.setReceiverType(ReceiverTypeEnum.REGISTRATION_ID);
		//params.setReceiverValue(registrationID);
		params.setReceiverType(ReceiverTypeEnum.TAG);
		params.setReceiverValue(tag);
	    LOG.info("msgTitle - " + msgTitle);
	    LOG.info("msgContent - " + msgContent);
	    LOG.info("TAG - " + ReceiverTypeEnum.TAG);
	    LOG.info("tag - " + tag);
	    LOG.info("################################################################");
		MessageResult msgResult = jpushClient.sendCustomMessage(msgTitle, msgContent, params, null);
        LOG.debug("responseContent - " + msgResult.responseResult.responseContent);
		if (msgResult.isResultOK()) {
	        LOG.info("msgResult - " + msgResult);
	        LOG.info("messageId - " + msgResult.getMessageId());
	        return "1";
		} else {
		    if (msgResult.getErrorCode() > 0) {
		        // 业务异常
		        LOG.warn("Service error - ErrorCode: "
		                + msgResult.getErrorCode() + ", ErrorMessage: "
		                + msgResult.getErrorMessage());
		        return "0";
		    } else {
		        // 未到达 JPush 
		        LOG.error("Other excepitons - "
		                + msgResult.responseResult.exceptionString);
		        return "-1";
		    }
		}
	}
	
	
	
	public static String SendMsgRegistrationID(String msgTitle,String msgContent) {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 0, DeviceEnum.Android, false);
		CustomMessageParams params = new CustomMessageParams();
		params.setReceiverType(ReceiverTypeEnum.APP_KEY);
		params.setReceiverValue(appKey);
		//params.setReceiverType(ReceiverTypeEnum.TAG);
		//params.setReceiverValue(tag);
	    LOG.info("msgTitle - " + msgTitle);
	    LOG.info("msgContent - " + msgContent);
	    LOG.info("TAG - " + ReceiverTypeEnum.TAG);
	    LOG.info("################################################################");
		MessageResult msgResult = jpushClient.sendCustomMessage(msgTitle, msgContent, params, null);
        LOG.debug("responseContent - " + msgResult.responseResult.responseContent);
		if (msgResult.isResultOK()) {
	        LOG.info("msgResult - " + msgResult);
	        LOG.info("messageId - " + msgResult.getMessageId());
	        return "1";
		} else {
		    if (msgResult.getErrorCode() > 0) {
		        // 业务异常
		        LOG.warn("Service error - ErrorCode: "
		                + msgResult.getErrorCode() + ", ErrorMessage: "
		                + msgResult.getErrorMessage());
		        return "0";
		    } else {
		        // 未到达 JPush 
		        LOG.error("Other excepitons - "
		                + msgResult.responseResult.exceptionString);
		        return "-1";
		    }
		}
	}

}

