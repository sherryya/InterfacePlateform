package com.rest.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Md5 工具类
 * @author SongJia
 *
 */
public class MD5 {

	public static String getMD5(String password) {
		if (password == null) {
            return null;
        }
        
        String hashtext  = null ;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
     
            byte[] messageDigest = md.digest( password.getBytes("UTF-8") );
		 
            BigInteger number = new BigInteger(1, messageDigest);
            hashtext  = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

        }
        catch (NoSuchAlgorithmException e) {
           
        }
        catch (UnsupportedEncodingException e) {
        	 throw new RuntimeException(e);
		}
        
        return hashtext;
	}

	public static void main(String[] args) {
		System.out.println(MD5.getMD5("123456"));
	}

}
