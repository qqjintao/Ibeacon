package com.ibeacon.service;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Login;

public interface LoginService extends BaseService<Login, Long>{
	/**
	 * 查询用户登录是否成功 
	 */
	public Login findByUsername(String userName,String password,String type);
	
	/**
	 * 判断用户名是否存在
	 */
	public Integer usernameExists(String userName,String type);
	
	/**
	 * 修改密码
	 */
	public boolean updatePassword(String mobilePhone,String passWord,String type);
	
	/**
	 * 通过id查询Login对象
	 */
	public Login findLoginById(Long id);
	
	/**
	 * 根据id获取密码
	 */
	public String findPasswordById(Long id);
	
	/**
	 * 分页查询
	 */
	public Page<Login> findLoginList(Login login,Pageable pageable);
}
