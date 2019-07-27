package com.ibeacon.dao;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.OldMan;

public interface OldManDao extends BaseDao<OldMan,Long>{
	
	public Page<OldMan> findOldManList(OldMan oldMan,Long id,Pageable pageable);
}
