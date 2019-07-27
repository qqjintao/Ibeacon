package com.ibeacon.dao;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Login;

public interface LoginDao extends BaseDao<Login,Long>{
	public Login findByUsername(String userName,String password,String type);
	
	public Integer usernameExists(String userName,String type);
	
	public boolean updatePassword(String mobilePhone,String passWord,String type);
	
	public Login findLoginById(Long id);
	
	public String findPasswordById(Long id);
	
	public Page<Login> findLoginList(Login login,Pageable pageable);
	
}
