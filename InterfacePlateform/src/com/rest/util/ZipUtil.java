package com.rest.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * 传输报文压缩类工具
 * @author Administrator
 *
 */
public class ZipUtil {

	
	public static byte[] compress(byte[] rawData) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(rawData);
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		if (bis != null) {
			byte[] buff = new byte[500]; // 定义一个500字节的缓冲区
			DeflaterOutputStream gzout = new DeflaterOutputStream(
					arrayOutputStream); // 数据压缩对象
			int len = 0;
			while ((len = bis.read(buff)) != -1) {
				gzout.write(buff, 0, len);
			}
			gzout.finish();
			gzout.close();
		}
		return arrayOutputStream.toByteArray();
	}

	/**
	 * 数据解压缩方法
	 * 
	 * @return 返回解压缩的数据流
	 * @throws IOException
	 */
	public static byte[] decompress(byte[] ripeData) throws IOException {
		if (ripeData != null) {
			int len = 0;
			byte[] buff = new byte[500];
			ByteArrayInputStream bin = new ByteArrayInputStream(ripeData);
			ripeData = null;
			InflaterInputStream gzipInput = new InflaterInputStream(bin);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((len = gzipInput.read(buff)) != -1) {
				out.write(buff, 0, len);
			}
			out.flush();
			out.close();
			gzipInput.close();
			return out.toByteArray();
		}
		return ripeData;

	}

	/**
	 * 数据解压缩方法
	 * 
	 * @return 返回解压缩的数据流
	 * @throws IOException
	 */
	public static final byte[] decompress(InputStream is, int streamLength) throws IOException {
		byte[] bufOut=new byte[streamLength];
		int offset = 0, readed = 0;
		while ((readed = is.read(bufOut, offset,streamLength - offset)) != -1) {
			offset += readed;
		}

		return decompress(bufOut);
	}


}
