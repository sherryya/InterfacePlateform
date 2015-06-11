package com.system;



import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertiesFactoryBean;

public class EncryptPropsFactoryBean extends PropertiesFactoryBean {
	/**
	 * 鍔犲瘑鐨勫瓧娈靛垪琛紝瀛楁涔嬮棿浣跨敤閫楀彿鍒嗗壊銆�
	 */
	private List<String> encryptFields;
	

	public List<String> getEncryptFields() {
		return encryptFields;
	}


	public void setEncryptFields(List<String> encryptFields) {
		this.encryptFields = encryptFields;
	}


	/**
	 * 鍒濆鍖栨柟娉曪紝闇�璁剧疆initParams鍙傛暟銆�
	 * @throws IOException
	 */
	final protected void init() throws IOException{
		Properties props = super.getObject();
		for(Object key: props.keySet()) {
			for(String encrpytField: encryptFields) {
				if (key.equals(encrpytField)) {
					props.setProperty((String) key, DESUtils.getDecryptString((String) props.get(key)));
				}
			}
			
		}
	}

}
