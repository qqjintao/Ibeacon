package com.ibeacon.service;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Ibeacon;
import com.ibeacon.form.IbeaconContainer;

public interface IbeaconService extends BaseService<Ibeacon, Long>{
	
	public Integer IsExists(String UUID);
	/**
	 * 分页查询Ibeacon列表
	 */
	public Page<IbeaconContainer> findIbeaconList(Ibeacon ibeacon,Long id,String oldman,Pageable pageable);
	
}
