package com.ibeacon.listener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ibeacon.entity.BaseEntity;
import com.ibeacon.entity.Login;

public class EntityListener {
	/**
	 * 保存前处理
	 * 
	 * @param entity
	 *            基类
	 */
	@PrePersist
	public void prePersist(BaseEntity entity) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		String name="system";
		if(login!=null){
			if(login.getType().equals("1")){
				name="顾客---"+login.getNickName();
			}else if(login.getType().equals("2")){
				name="企业---"+login.getNickName();
			}else{
				name="管理员---"+login.getNickName();
			}
		}
		entity.setCreateDate(new Date());
		entity.setCreateName(name);
		entity.setModifyDate(new Date());
		entity.setModifyName(name);
	}

	/**
	 * 更新前处理
	 * 
	 * @param entity
	 *            基类
	 */
	@PreUpdate
	public void preUpdate(BaseEntity entity) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		String name="system";
		if(login!=null){
			if(login.getType().equals("1")){
				name="顾客---"+login.getNickName();
			}else if(login.getType().equals("2")){
				name="企业---"+login.getNickName();
			}else{
				name="管理员---"+login.getNickName();
			}
		}
		entity.setModifyDate(new Date());
		entity.setModifyName(name);
	}
}
