package com.main;

import org.apache.log4j.Logger;

import com.crontab.CronAutoClacCanStream;

public class Common {
	
	private static final Logger log = Logger.getLogger(Common.class.getName());
	/**
	 * 数据校验
	 * @param arr
	 * @return
	 */
	public static int calcHex(String[] arr) {
		long sum_Hex = 0;
		String Hex = arr[arr.length - 1];
		for (int i = 0; i < arr.length - 1; i++) {
			sum_Hex = sum_Hex + Long.parseLong(arr[i], 16);
		}
	    int FF=0xff;
	    long a=sum_Hex ^ FF;//两者取异或运算
	    String str_Hex=Integer.toHexString((int)a);
	    if(str_Hex.length()>=3)
	    {
	    	 str_Hex=str_Hex.substring(str_Hex.length()-2);
	    }
	    else if(str_Hex.length()==1)
	    {
	    	str_Hex="0"+str_Hex;
	    }
	    if(Hex.equalsIgnoreCase(str_Hex))
	    {
	    	return 1;
	    }
	    else
	    {
	    	return 0;
	    }
	}
	/**
	 * 16进制转2进制
	 * @param hexString
	 * @return
	 */
	public static String hexString2binaryString(String hexString)
	{
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++)
		{
			tmp = "0000"+ Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}
	/**
	 * 二进制转16进制
	 * @param bString
	 * @return
	 */
	public static String binaryString2hexString(String bString)
	{
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4)
		{
			iTmp = 0;
			for (int j = 0; j < 4; j++)
			{
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}
	//2E410D0200000DAC04B000B400138819DA    2E8101017C
	//字符重新  去掉无效字符
	//2E410D0205DD083D05B4FF51000466123
	public static String[] arr(String str) {
		//log.info("CAN数据解析前：*********************************:" + str);
		String len = str.substring(4, 6);
		Integer i_len = Integer.parseInt(len, 16);// 16进制转10进制
		if (str.length() != (i_len * 2 + 8)) { //指令格式不正确
			return null;
		} else {
			//System.out.println(i_len);
			String sub_str = str.substring(0, i_len * 2 + 8);
			String[] arr1 = new String[(i_len * 2 + 8) / 2];
			for (int i = 0; i < (i_len * 2 + 8) / 2; i++) {
				String s = sub_str.substring(i * 2, i * 2 + 2);
				arr1[i] = s;
			}
			String[] arr2 = new String[arr1.length - 1];
			for (int j = 1; j < arr1.length; j++) {
				arr2[j - 1] = arr1[j];
			}
			return arr2;
		}
	}
    /**
     * 将16进制的字符串转成16进制数
     * @param inputStr
     * @return
     */
	public static byte[] toStringHex(String inputStr) {
		byte[] result = new byte[inputStr.length() / 2];
		for (int i = 0; i < inputStr.length() / 2; ++i)
			result[i] = (byte) (Integer.parseInt(inputStr.substring(i * 2, i * 2 + 2), 16) & 0xff);
		return result;
	}

	public static void main(String[] args) {
		String [] arr1=arr("2E41140207A00000000004ECFFF6000FD8008000000000B5");
		int i=calcHex(arr1);
		System.out.println(i);
		//System.out.println(Integer.toHexString("DF"));
	  /* byte[] a=toStringHex("0322");
	   int i= (a[0]+(a[1]<<8))/16 ;
	   System.out.println(i);*/
	}
}
