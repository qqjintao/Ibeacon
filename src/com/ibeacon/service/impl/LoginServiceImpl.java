package com.ibeacon.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.LoginDao;
import com.ibeacon.entity.Login;
import com.ibeacon.service.LoginService;

@Service("loginServiceImpl")
public class LoginServiceImpl extends BaseServiceImpl<Login, Long> implements LoginService{
	
	@Resource(name = "loginDaoImpl")
	private LoginDao loginDao;
	
	@Resource(name = "loginDaoImpl")
	public void setBaseDao(LoginDao loginDao) {
		super.setBaseDao(loginDao);
	}
	
	public Login findByUsername(String userName,String password,String type) {
		return loginDao.findByUsername(userName,password,type);
	}
	
	public Integer usernameExists(String userName,String type){
		return loginDao.usernameExists(userName,type);
	}

	@Transactional(readOnly = true)
	public boolean updatePassword(String mobilePhone,String passWord,String type){
		return loginDao.updatePassword(mobilePhone,passWord,type);
	}
	
	public Login findLoginById(Long id){
		return loginDao.findLoginById(id);
	}
	
	public String findPasswordById(Long id){
		return loginDao.findPasswordById(id);
	}
	
	public Page<Login> findLoginList(Login login,Pageable pageable){
		return loginDao.findLoginList(login,pageable);
	}
}
