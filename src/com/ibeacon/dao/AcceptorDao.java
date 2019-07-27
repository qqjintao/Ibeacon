package com.ibeacon.dao;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Acceptor;

public interface AcceptorDao extends BaseDao<Acceptor,Long>{
	
	public Page<Acceptor> findAcceptorList(Acceptor acceptor,Long id,Pageable pageable);

}
