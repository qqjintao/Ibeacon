package com.ibeacon.service;

import java.util.List;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Info;
import com.ibeacon.entity.Login;

public interface InfoService extends BaseService<Info, Long>{
	/**
	 * 分页查询Info列表
	 */
	public Page<Info> findInfoList(Info info,Long id,Pageable pageable);
	
	/**
	 * 导入功能 批量插入数据
	 */
	public void addList(List<List<Object>> results,Login login);
}
