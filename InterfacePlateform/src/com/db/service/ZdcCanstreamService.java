package com.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.db.dao.ZdcCanstreamMapper;
import com.db.dto.ZDCanstreamTempSave;
import com.db.dto.ZdcCanBusstream;
import com.db.dto.ZdcCanstream;
import com.db.dto.ZdcMileage;

@Service
public class ZdcCanstreamService {
	@Autowired
	private ZdcCanstreamMapper zdcCanstreamMapper;

	public void insert(ZdcCanstream zdcCanstream) {
		zdcCanstreamMapper.insert(zdcCanstream);
	}

	public void insertBus(List<ZdcCanBusstream> streamBus) {
		zdcCanstreamMapper.insertBus(streamBus);
	}

	public ZdcCanstream selectForID1(ZdcCanstream zdcCanstream) {
		return zdcCanstreamMapper.selectForID1(zdcCanstream);
	}

	public ZdcCanstream selectForID2(ZdcCanstream zdcCanstream) {
		return zdcCanstreamMapper.selectForID2(zdcCanstream);
	}

	public List<ZdcCanstream> selectForID3(ZdcCanstream zdcCanstream) {
		return zdcCanstreamMapper.selectForID3(zdcCanstream);
	}

	/**
	 * 将计算完里程信息的记录标记为里程已经计算完成 Mileage_Finish=1
	 * 
	 * @param id
	 * @return
	 */
	public int updateMileFlag(long id) {
		return zdcCanstreamMapper.updateMileFlag(id);
	}
	/**
	 * 根据设备id查询里程id
	 * @param deviceid
	 * @return
	 */
	public Long selectIdByDeviceId(String deviceid)
	{
		return zdcCanstreamMapper.selectIdByDeviceId(deviceid);
	}
	/**
	 * 根据设备id更新里程id
	 * @param tempID
	 * @return
	 */
	public int updateCanStreamID(ZDCanstreamTempSave tempID)
	{
		return zdcCanstreamMapper.updateCanStreamID(tempID);
	}
	/**
	 * 根据设备id插入里程id
	 * @param tempID
	 * @return
	 */
	public int insertCanStreamID(ZDCanstreamTempSave tempID)
	{
		return zdcCanstreamMapper.insertCanStreamID(tempID);
	}
	/**
	 * 根据设备id和里程结束的id查询当前里程中是否存在里程启动的异常gps数据,如：平板突然没电的情况
	 * @param zdcCanstream
	 * @return
	 */
	public List<ZdcCanstream> selectUnfinished(ZdcCanstream zdcCanstream)
	{
		return zdcCanstreamMapper.selectUnfinished(zdcCanstream);
	}
	/**
	 * 查询最新已调配gps信息
	 * @param deviceuid
	 * @return
	 */
	public ZdcCanstream selectCanByDeviceId(String deviceuid)
	{
		return zdcCanstreamMapper.selectCanByDeviceId(deviceuid);
	}
}
