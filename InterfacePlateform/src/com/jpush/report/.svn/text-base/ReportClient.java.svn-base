package com.jpush.report;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jpush.common.BaseHttpClient;
import com.jpush.common.ResponseResult;
import com.jpush.common.ValidateRequestParams;
import com.jpush.report.ReceivedsResult.Received;
import com.jpush.utils.StringUtils;

public class ReportClient extends BaseHttpClient {    
    private static final String REPORT_HOST_NAME = "https://report.jpush.cn";
    private static final String REPORT_RECEIVE_PATH = "/v2/received";

	private static Gson _gson = new Gson();
	
	private String _appKey;
	private String _masterSecret;
	
	public ReportClient(String masterSecret, String appKey) {
        this._masterSecret = masterSecret;
	    this._appKey = appKey;
	}
	
	
    public ReceivedsResult getReceiveds(String[] msgIdArray) {
        return getReceiveds(StringUtils.arrayToString(msgIdArray));
    }
	
    public ReceivedsResult getReceiveds(String msgIds) {
        String authCode = getAuthorizationBase64(_appKey, _masterSecret);
        return getResportReceived(msgIds, authCode);
	}
	
    public ReceivedsResult getResportReceived(String msgIds, String authCode) {
        ValidateRequestParams.checkReportParams(_appKey, _masterSecret, msgIds);
        
        String url = REPORT_HOST_NAME + REPORT_RECEIVE_PATH + "?msg_ids=" + msgIds;
        ResponseResult result = sendGet(url, true, null, authCode);
        
        ReceivedsResult receivedsResult = new ReceivedsResult();
        if (result.responseCode == RESPONSE_OK) {
            Type listType = new TypeToken<List<Received>>(){}.getType();
            receivedsResult.receivedList = _gson.fromJson(result.responseContent, listType);
        }
        
        receivedsResult.responseResult = result;
        return receivedsResult;
    }

}



