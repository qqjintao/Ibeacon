package com.ibeacon.dao;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.IBeaconInput;
import com.ibeacon.form.IBeaconVo;

public interface IBeaconInputDao extends BaseDao<IBeaconInput,Long>{
	
	public Page<IBeaconInput> findIBeaconInputList(IBeaconInput iBeaconInput,Pageable pageable);
	
	public Page<IBeaconVo> findIBeaconInput(IBeaconInput iBeaconInput,String oldMan,String acceptorName,String address,Long LoginId,Pageable pageable);
}
