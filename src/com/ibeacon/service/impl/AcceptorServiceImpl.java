package com.ibeacon.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.AcceptorDao;
import com.ibeacon.entity.Acceptor;
import com.ibeacon.service.AcceptorService;

@Service("acceptorServiceImpl")
public class AcceptorServiceImpl extends BaseServiceImpl<Acceptor, Long> implements AcceptorService {

	@Resource(name = "acceptorDaoImpl")
	private AcceptorDao acceptorDao;

	@Resource(name = "acceptorDaoImpl")
	public void setBaseDao(AcceptorDao acceptorDao) {
		super.setBaseDao(acceptorDao);
	}

	public Page<Acceptor> findAcceptorList(Acceptor acceptor, Long id, Pageable pageable) {
		return acceptorDao.findAcceptorList(acceptor, id, pageable);
	}

}
