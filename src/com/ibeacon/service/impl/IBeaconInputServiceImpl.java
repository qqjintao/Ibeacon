package com.ibeacon.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.IBeaconInputDao;
import com.ibeacon.entity.IBeaconInput;
import com.ibeacon.form.IBeaconVo;
import com.ibeacon.service.IBeaconInputService;

@Service("iBeaconInputServiceImpl")
public class IBeaconInputServiceImpl extends BaseServiceImpl<IBeaconInput, Long> implements IBeaconInputService{
	@Resource(name = "iBeaconInputDaoImpl")
	private IBeaconInputDao iBeaconInputDao;
	
	@Resource(name = "iBeaconInputDaoImpl")
	public void setBaseDao(IBeaconInputDao iBeaconInputDao) {
		super.setBaseDao(iBeaconInputDao);
	}
	
	public Page<IBeaconInput> findIBeaconInputList(IBeaconInput iBeaconInput,Pageable pageable){
		return iBeaconInputDao.findIBeaconInputList(iBeaconInput,pageable);
	}
	
	public Page<IBeaconVo> findIBeaconInput(IBeaconInput iBeaconInput,String oldMan,String acceptorName,String address,Long LoginId,Pageable pageable){
		return iBeaconInputDao.findIBeaconInput(iBeaconInput, oldMan, acceptorName, address, LoginId, pageable);
	}
}
