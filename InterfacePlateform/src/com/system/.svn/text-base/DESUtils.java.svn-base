package com.system;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtils {
	private static Key key;
	private static String KEY_STR = "boboKey";
	static {
		try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
          	secureRandom.setSeed(KEY_STR.getBytes());
			generator.init(secureRandom);  
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 锟斤拷str锟斤拷锟斤拷DES锟斤拷锟斤拷
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str) {
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byte[] strBytes = str.getBytes("UTF8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			return base64en.encode(encryptStrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 锟斤拷str锟斤拷锟斤拷DES锟斤拷锟斤拷
	 * 
	 * @param str
	 * @return
	 */
	public static String getDecryptString(String str) {
		BASE64Decoder base64De = new BASE64Decoder();
		try {
			byte[] strBytes = base64De.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptStrBytes = cipher.doFinal(strBytes);
			return new String(decryptStrBytes, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void main(String[] args) throws Exception {
		System.out.println(":" + getEncryptString("root"));
		System.out.println(":" + getEncryptString("toor@!456"));
		;
	}
}
