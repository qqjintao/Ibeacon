package com.ibeacon.service;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Acceptor;

public interface AcceptorService extends BaseService<Acceptor, Long>{
	/**
	 * 分页查询Acceptor列表
	 */
	public Page<Acceptor> findAcceptorList(Acceptor acceptor,Long id,Pageable pageable);
	
}