package com.ibeacon.service;

import com.ibeacon.entity.Fence;

public interface FenceService extends BaseService<Fence, Long>{
	/**
	 * 通过登录id查找电子围栏模板 
	 */
	public Fence findByLoginId(Long id);
	
}
