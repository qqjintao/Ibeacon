package com.ibeacon.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibeacon.dao.FenceDao;
import com.ibeacon.entity.Fence;
import com.ibeacon.service.FenceService;

@Service("fenceServiceImpl")
public class FenceServiceImpl extends BaseServiceImpl<Fence, Long> implements FenceService{
	@Resource(name = "fenceDaoImpl")
	private FenceDao fenceDao;
	
	@Resource(name = "fenceDaoImpl")
	public void setBaseDao(FenceDao fenceDao) {
		super.setBaseDao(fenceDao);
	}
		
	public Fence findByLoginId(Long id){
		return fenceDao.findByLoginId(id);
	}
}
