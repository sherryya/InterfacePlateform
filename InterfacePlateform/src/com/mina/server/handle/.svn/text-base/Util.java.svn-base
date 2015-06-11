package com.mina.server.handle;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		String restr= stringBuilder.toString();
		stringBuilder=null;
		return restr;
	}
	
	public static byte[] HexStringToBytes(String inputStr) {
		byte[] result = new byte[inputStr.length() / 2];
		for (int i = 0; i < inputStr.length() / 2; ++i)
			result[i] = (byte) (Integer.parseInt( inputStr.substring(i * 2, i * 2 + 2), 16) & 0xff);
		return result;
	}
	
	public static byte[] hexStringToBytes(String hexString) {   
	    if (hexString == null || hexString.equals("")) {   
	        return null;   
	    }   
	    hexString = hexString.toUpperCase();   
	    int length = hexString.length() / 2;   
	    char[] hexChars = hexString.toCharArray();   
	    byte[] d = new byte[length];   
	    for (int i = 0; i < length; i++) {   
	        int pos = i * 2;   
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
	    }   
	    return d;   
	}   
	/**  
	 * Convert char to byte  
	 * @param c char  
	 * @return byte  
	 */  
	 private static byte charToByte(char c) {   
	    return (byte) "0123456789ABCDEF".indexOf(c);   
	}  
	
	/**
	 * 鍘绘�?��囦护鏍囪瘑锟�?	 * @param order   2E2403000000D8     2E24020000D9
	 * @return
	 */
	public static String getRigthOrder(String order)
	{
		int len1=Integer.valueOf(order.substring(6, 8));
		len1=len1+1;
		String check=order.substring(order.length()-2, order.length());
		long y = Long.parseLong(check, 16);
		check=Long.toHexString(y+len1).toUpperCase();
		if(check.length()==1)
		{
			check="0"+check;
		}
		String head=order.substring(0, 4);
		String l=order.substring(4, 6);
		l=Long.toHexString(Long.parseLong(l, 16)-1).toUpperCase();
		String len="";
		if(String.valueOf(l).length()==1)
		{
			len="0"+l;
		}
		else
		{
			len=l;
		}
		String data=order.substring(8, order.length()-2);
		//System.out.println(head+len+data+check);
		return head+len+data+check;
	}
	
	public static void main(String[] args) {
		String dd=getRigthOrder("2E41030101B009");
		System.out.println(dd);
	}
	
	public static Boolean ordersVAL(String order)
	{
	
		String arr[]=new String[]{};
		//arr=MyConstant.ORDERS_VAL.split(",");
		int flag=0;
		for(String str:arr)
		{
			if(order.contains(str))
			{
				flag+=1;
			}
		}
		if(flag==arr.length)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static Boolean date_diff(String dt1,String dt2,long sec) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1;
		d1 = df.parse(dt1);
		Date d2;
		d2 = df.parse(dt2);
		long diff = d1.getTime() - d2.getTime();
		System.out.println(diff);
		if (diff > sec * 1000)
			return true;
		else
			return false;
	}
	
	public static String getGPSFileName() {
		long time = System.currentTimeMillis() - 60000;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		Date d1 = new Date(time);
		String fileName = format.format(d1);
		return fileName + ".gps";
	}
	public static String getCurrDateTime() {
		long time = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date(time);
		String d = format.format(d1);
		return d;
	}
	public static String getFileName() {
		long time = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		Date d1 = new Date(time);
		String fileName = format.format(d1);
		return fileName + ".gps";
	}
	
	
	/**
	 * 寰楀埌姝ｇ�?鐨勬晠闅滅爜淇℃伅锛屼繚瀛樺埌骞冲彴
	 * @param fault_code
	 * 2E510400000000AA#  鏃犳晠闅�?��鎭棤锟�锟斤拷瀛樺埌鏁版嵁锟�
       2E510104A9#  娌℃湁寰�?��鏁呴殰淇℃伅
       2E5101FFAE#  瓒呮椂娌℃湁寰楀埌鏁呴殰淇℃�?
                    浠ヤ笂涓夌被淇℃伅鏃犻渶淇濆瓨鍒版暟鎹�?
	 * @return
	 */
	public static boolean getFaultCode(String fault_code)
	{
		
		if(fault_code.toString().length()>=16)
		{
			String code=fault_code.toString().substring(8, 14);
			if(code.equalsIgnoreCase("000000"))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
		
	}
	

}
