package com.ibeacon.dao;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Ibeacon;
import com.ibeacon.form.IbeaconContainer;

public interface IbeaconDao extends BaseDao<Ibeacon,Long>{
	
	public Integer IsExists(String UUID);
	
	public Page<IbeaconContainer> findIbeaconList(Ibeacon ibeacon,Long id,String oldman,Pageable pageable);
}
