package com.ibeacon.service;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.IBeaconInput;
import com.ibeacon.form.IBeaconVo;

public interface IBeaconInputService extends BaseService<IBeaconInput, Long>{
	/**
	 * 分页查询IBeaconInput列表
	 */
	public Page<IBeaconInput> findIBeaconInputList(IBeaconInput iBeaconInput,Pageable pageable);
	
	public Page<IBeaconVo> findIBeaconInput(IBeaconInput iBeaconInput,String oldMan,String acceptorName,String address,Long LoginId,Pageable pageable);
}
