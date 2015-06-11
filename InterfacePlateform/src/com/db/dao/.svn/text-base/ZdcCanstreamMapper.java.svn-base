package com.db.dao;

import java.util.List;

import com.db.dto.ZDCanstreamTempSave;
import com.db.dto.ZdcCanBusstream;
import com.db.dto.ZdcCanstream;
import com.db.dto.ZdcMileage;

public interface ZdcCanstreamMapper {
	void insert(ZdcCanstream zdcCanstream);

	ZdcCanstream selectForID1(ZdcCanstream zdcCanstream);

	ZdcCanstream selectForID2(ZdcCanstream zdcCanstream);

	List<ZdcCanstream> selectForID3(ZdcCanstream zdcCanstream);

	int updateMileFlag(Long id);

	void insertBus(List<ZdcCanBusstream> streamBus);
	/**
	 * 根据设备id查询里程id
	 * @param deviceid
	 * @return
	 */
	Long selectIdByDeviceId(String deviceid);
	/**
	 * 根据设备id更新里程id
	 * @param tempID
	 * @return
	 */
	int updateCanStreamID(ZDCanstreamTempSave tempID);
	/**
	 * 根据设备id插入里程id
	 * @param tempID
	 * @return
	 */
	int insertCanStreamID(ZDCanstreamTempSave tempID);
	
	List<ZdcCanstream> selectUnfinished(ZdcCanstream zdcCanstream);
	/**
	 * 查询最新已调配gps信息
	 * @param deviceuid
	 * @return
	 */
	ZdcCanstream selectCanByDeviceId(String deviceuid);
	
	
}
