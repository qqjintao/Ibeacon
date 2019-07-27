package com.ibeacon.dao;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Info;

public interface InfoDao extends BaseDao<Info,Long>{
	
	public Page<Info> findInfoList(Info info,Long id,Pageable pageable);

}
