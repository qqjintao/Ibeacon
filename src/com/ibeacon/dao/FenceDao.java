package com.ibeacon.dao;

import com.ibeacon.entity.Fence;

public interface FenceDao extends BaseDao<Fence,Long>{
	
	public Fence findByLoginId(Long id);
}
