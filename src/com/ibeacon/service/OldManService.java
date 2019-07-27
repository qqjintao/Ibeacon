package com.ibeacon.service;

import java.util.List;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Login;
import com.ibeacon.entity.OldMan;

public interface OldManService extends BaseService<OldMan, Long>{
	/**
	 * 分页查询OldMan列表
	 */
	public Page<OldMan> findOldManList(OldMan oldMan,Long id,Pageable pageable);
	
	/**
	 * 导入功能 批量插入数据
	 */
	public void addList(List<List<Object>> results,Login login);
}
