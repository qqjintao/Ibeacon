package com.ibeacon.dao.impl;

import java.math.BigInteger;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.LoginDao;
import com.ibeacon.entity.Login;

@Repository("loginDaoImpl")
public class LoginDaoImpl extends BaseDaoImpl<Login, Long> implements LoginDao{
	
	public Login findByUsername(String userName,String password,String type) {
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT login FROM Login login WHERE ");
			sql.append("(login.isLocked='N' AND lower(login.mailBox) = lower(:userName) and login.passWord = :password and login.type = :type) ");
			sql.append("OR ");
			sql.append("(login.isLocked='N' AND login.mobilePhone = :userName and login.passWord = :password and login.type = :type )");
			sql.append("OR ");
			sql.append("(login.isLocked='N' AND lower(login.nickName) = lower(:userName) and login.passWord = :password and login.type = :type)");
			return entityManager.createQuery(sql.toString(),Login.class).setFlushMode(FlushModeType.COMMIT)
			.setParameter("userName",userName).setParameter("password", password)
			.setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void save(Login login){
		this.persist(login);
	}
	
	public void update(Login login){
		this.merge(login);
	}
	
	public Integer usernameExists(String userName,String type){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT count(1) FROM login WHERE login.type = :type AND ");
			sql.append("(lower(login.mail_box) = lower(:userName)  ");
			sql.append("OR ");
			sql.append("login.mobile_phone = :userName ");
			sql.append("OR ");
			sql.append("lower(login.nick_name) = lower(:userName))");
			String result=entityManager.createNativeQuery(sql.toString()).setFlushMode(FlushModeType.COMMIT)
			.setParameter("userName",userName).setParameter("type", type).getResultList().get(0).toString();
			int count;
			if(result==null){
				count=-1;
			}else if(result.equals("0")){
				count=0;
			}else{
				count=1;
			}
			return  count;
		} catch (NoResultException e) {
			return -1;
		}
	}
	
	public boolean updatePassword(String mobilePhone,String passWord,String type){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("UPDATE login SET login.pass_word = :passWord ");
			sql.append("WHERE login.mobile_phone = :mobilePhone ");
			sql.append("AND login.type = :type");
			entityManager.createNativeQuery(sql.toString()).setFlushMode(FlushModeType.COMMIT)
			.setParameter("passWord", passWord).setParameter("mobilePhone",mobilePhone)
			.setParameter("type", type).executeUpdate();
			return  true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	public Login findLoginById(Long id){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT login FROM Login login WHERE login.id = :id ");
			return entityManager.createQuery(sql.toString(),Login.class).setFlushMode(FlushModeType.COMMIT)
			.setParameter("id",id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public String findPasswordById(Long id){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT login FROM Login login WHERE login.id = :id ");
			return entityManager.createQuery(sql.toString(),Login.class).setFlushMode(FlushModeType.COMMIT)
			.setParameter("id",id).getSingleResult().getPassWord();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Page<Login> findLoginList(Login login,Pageable pageable){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT * FROM login  WHERE 1=1 ");
			StringBuffer countSql=new StringBuffer();
			countSql.append("SELECT COUNT(1) FROM login WHERE 1=1 ");
			if(login!=null){
			if(login.getId()!=null){
				sql.append("and id = :id ");
				countSql.append("and id = :id ");
			}
			if(login.getMobilePhone()!=null){
				sql.append("and mobile_phone = :mobilePhone ");
				countSql.append("and mobile_phone = :mobilePhone ");
			}
			if(login.getMailBox()!=null){
				sql.append("and mail_box like :mailBox ");
				countSql.append("and mail_box like :mailBox ");
			}
			if(login.getNickName()!=null){
				sql.append("and nick_name like :nickName ");
				countSql.append("and nick_name like :nickName ");
			}
			if(login.getType()!=null){
				sql.append("and type = :type ");
				countSql.append("and type = :type ");
			}
			}
			TypedQuery<Login> query = (TypedQuery<Login>) entityManager.createNativeQuery(sql.toString(),Login.class)
					.setFlushMode(FlushModeType.COMMIT);
			TypedQuery<BigInteger> countQuery=(TypedQuery<BigInteger>) entityManager.createNativeQuery(countSql.toString()).setFlushMode(FlushModeType.COMMIT);
			if(login!=null){
			if(login.getId()!=null){
				query.setParameter("id", login.getId());
				countQuery.setParameter("id", login.getId());
			}
			if(login.getMobilePhone()!=null){
				query.setParameter("mobilePhone", login.getMobilePhone());
				countQuery.setParameter("mobilePhone", login.getMobilePhone());
			}
			if(login.getMailBox()!=null){
				query.setParameter("mailBox", "%"+login.getMailBox()+"%");
				countQuery.setParameter("mailBox", "%"+login.getMailBox()+"%");
			}
			if(login.getNickName()!=null){
				query.setParameter("nickName", "%"+login.getNickName()+"%");
				countQuery.setParameter("nickName", "%"+login.getNickName()+"%");
			}
			if(login.getType()!=null){
				query.setParameter("type", login.getType());
				countQuery.setParameter("type", login.getType());
			}	
			}
			int total = Integer.valueOf(countQuery.getResultList().get(0).toString());
			int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
			if (totalPages < pageable.getPageNumber()) {
				pageable.setPageNumber(totalPages);
			}
			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());
			return new Page<Login>(query.getResultList(), total, pageable);
		} catch (NoResultException e) {
			return null;
		}
	}
}
