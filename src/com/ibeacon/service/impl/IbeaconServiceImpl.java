package com.ibeacon.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.IbeaconDao;
import com.ibeacon.entity.Ibeacon;
import com.ibeacon.form.IbeaconContainer;
import com.ibeacon.service.IbeaconService;

@Service("ibeaconServiceImpl")
public class IbeaconServiceImpl extends BaseServiceImpl<Ibeacon, Long> implements IbeaconService{
	
	@Resource(name = "ibeaconDaoImpl")
	private IbeaconDao ibeaconDao;
	
	@Resource(name = "ibeaconDaoImpl")
	public void setBaseDao(IbeaconDao ibeaconDao) {
		super.setBaseDao(ibeaconDao);
	}

	public Integer IsExists(String UUID){
		return ibeaconDao.IsExists(UUID);
	}
	
	public Page<IbeaconContainer> findIbeaconList(Ibeacon ibeacon,Long id,String oldman,Pageable pageable){
		return ibeaconDao.findIbeaconList(ibeacon,id,oldman,pageable);
	}
	
}
