package com.jpush.report;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.jpush.common.BaseResult;
import com.jpush.common.ResponseResult.ErrorObject;

public class ReceivedsResult extends BaseResult {
    @Expose public List<Received> receivedList = new ArrayList<Received>();
	
	public static class Received {
	    @Expose public long msg_id;
	    @Expose public int android_received;
	    @Expose public int ios_apns_sent;
	}
	
	public List<Received> getReceivedList() {
	    return this.receivedList;
	}
	
    protected ErrorObject getErrorObject() {
        if (null != responseResult) {
            return responseResult.error;
        }
        return null;
    }
    
	public int getErrorCode() {
	    ErrorObject eo = getErrorObject();
	    if (null != eo) {
	        return eo.code;
	    }
	    return ERROR_CODE_OK;
	}
	
	public String getErrorMessage() {
	    ErrorObject eo = getErrorObject();
	    if (null != eo) {
	        return eo.message;
	    }
	    return ERROR_MESSAGE_NONE;
	}
	
	public boolean isResultOK() {
	    if (responseResult.responseCode == RESPONSE_OK) return true;
	    return false;
	}
	
	@Override
	public String toString() {
		return _gson.toJson(this);
	}
}
