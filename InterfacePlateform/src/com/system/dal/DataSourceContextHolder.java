package com.system.dal;

/**
 * DataSource涓婁笅鏂囧彞鏌勶紝閫氳繃姝ょ被璁剧疆闇�璁块棶鐨勫搴旀暟鎹簮
 * 
 */
public class DataSourceContextHolder {
	/**
	 * DataSource涓婁笅鏂囷紝姣忎釜绾跨▼瀵瑰簲鐩稿簲鐨勬暟鎹簮key
	 */
	private static final ThreadLocal<DataSourceInfo> contextHolder = new ThreadLocal<DataSourceInfo>();

	/**
	 * 鏁版嵁婧愮殑璋冪敤鍙傛暟缁熻
	 */
	private static final ThreadLocal<Integer> contextCounter = new ThreadLocal<Integer>();

	/**
	 * 寰楀埌涓嬩竴涓鏁般�
	 * @return
	 */
	public static Integer getNextCounter() {
		Integer curCounter = contextCounter.get();
		if (curCounter == null) {
			curCounter = new Integer(0);
		}
		curCounter ++;
		if (curCounter < 0) {
			curCounter = 0;
		}
		contextCounter.set(curCounter);
		return curCounter;
	}
	
	
	/**
	 * 寰楀埌褰撳墠绾跨▼鐨勬暟鎹簮銆�
	 * @param dataSourceType
	 */
	public static DataSourceInfo getDataSource() {
		return contextHolder.get();
	}

	/**
	 * 璁剧疆褰撳墠绾跨▼鐨勬暟鎹簮
	 * @param dataSourceType
	 */
	public static void setDataSource(DataSourceInfo dataSourceInfo) {
		contextHolder.set(dataSourceInfo);
	}
	
	/**
	 * 寰楀埌褰撳墠绾跨▼鐨勬暟鎹簮鍚嶇О
	 * @return
	 */
	public static String getDataSourceName() {
		if (contextHolder.get() != null) {
			return contextHolder.get().getDataSourceName();
		} else {
			return "";	//杩斿洖绌鸿〃绀猴紝浣跨敤榛樿鏁版嵁婧愶紝鍗砈pring閰嶇疆鏂囦欢涓殑defaultTargetDataSource銆�
		}
	}

	/**
	 * 娓呴櫎褰撳墠绾跨▼鏁版嵁婧愬悕绉�
	 */
	public static void clearDataSource() {
		contextHolder.remove();
	}
}
