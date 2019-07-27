package com.ibeacon.dao.impl;

import java.math.BigInteger;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.AcceptorDao;
import com.ibeacon.entity.Acceptor;

@Repository("acceptorDaoImpl")
public class AcceptorDaoImpl extends BaseDaoImpl<Acceptor, Long> implements AcceptorDao{
	public void save(Acceptor acceptor){
		this.persist(acceptor);
	}
	
	public void update(Acceptor acceptor){
		this.merge(acceptor);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Acceptor> findAcceptorList(Acceptor acceptor,Long id,Pageable pageable){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT acceptor.* FROM acceptor JOIN login_acceptor ON login_acceptor.acceptor=acceptor.id WHERE 1=1 ");
			StringBuffer countSql=new StringBuffer();
			countSql.append("SELECT COUNT(1) FROM acceptor JOIN login_acceptor ON login_acceptor.acceptor=acceptor.id WHERE 1=1 ");	
			if(id!=null){
				sql.append("and login_acceptor.login = :id ");
				countSql.append("and login_acceptor.login = :id ");
			}
			if(acceptor.getName()!=null){
				sql.append("and acceptor.`name` like :name ");
				countSql.append("and acceptor.`name` like :name ");
			}
			if(acceptor.getAddress()!=null){
				sql.append("and acceptor.`address` like :address ");
				countSql.append("and acceptor.`address` like :address ");
			}
			if(acceptor.getAlarmTime()!=null){
				sql.append("and acceptor.`alarm_time` = :alarm_time ");
				countSql.append("and acceptor.`alarm_time` = :alarm_time ");
			}
			if(acceptor.getAlarmVoice()!=null){
				sql.append("and acceptor.`alarm_voice` = :alarm_voice ");
				countSql.append("and acceptor.`alarm_voice` = :alarm_voice ");
			}
			if(acceptor.getIsSend()!=null){
				sql.append("and acceptor.`is_send` = :is_send ");
				countSql.append("and acceptor.`is_send` = :is_send	");
			}
			if(acceptor.getPhone()!=null){
				sql.append("and acceptor.`phone` like :phone ");
				countSql.append("and acceptor.`phone` like :phone ");
			}
			if(acceptor.getRemark()!=null){
				sql.append("and acceptor.remark like :remark ");
				countSql.append("and acceptor.remark like :remark ");
			}
			TypedQuery<Acceptor> query = (TypedQuery<Acceptor>) entityManager.createNativeQuery(sql.toString(),Acceptor.class)
					.setFlushMode(FlushModeType.COMMIT);
			TypedQuery<BigInteger> countQuery=(TypedQuery<BigInteger>) entityManager.createNativeQuery(countSql.toString()).setFlushMode(FlushModeType.COMMIT);
			if(id!=null){
				query.setParameter("id", id);
				countQuery.setParameter("id", id);
			}
			if(acceptor.getName()!=null){
				query.setParameter("name", "%"+acceptor.getName()+"%");
				countQuery.setParameter("name", "%"+acceptor.getName()+"%");
			}
			if(acceptor.getAddress()!=null){
				query.setParameter("address", "%"+acceptor.getAddress()+"%");
				countQuery.setParameter("address", "%"+acceptor.getAddress()+"%");
			}
			if(acceptor.getAlarmTime()!=null){
				query.setParameter("alarm_time", acceptor.getAlarmTime());
				countQuery.setParameter("alarm_time", acceptor.getAlarmTime());
			}
			if(acceptor.getAlarmVoice()!=null){
				query.setParameter("alarm_voice", acceptor.getAlarmVoice());
				countQuery.setParameter("alarm_voice", acceptor.getAlarmVoice());
			}
			if(acceptor.getIsSend()!=null){
				query.setParameter("is_send", acceptor.getIsSend());
				countQuery.setParameter("is_send", acceptor.getIsSend());
			}
			if(acceptor.getPhone()!=null){
				query.setParameter("phone", "%"+acceptor.getPhone()+"%");
				countQuery.setParameter("phone", "%"+acceptor.getPhone()+"%");
			}
			if(acceptor.getRemark()!=null){
				query.setParameter("remark", "%"+acceptor.getRemark()+"%");
				countQuery.setParameter("remark", "%"+acceptor.getRemark()+"%");
			}
			int total = Integer.valueOf(countQuery.getResultList().get(0).toString());
			int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
			if (totalPages < pageable.getPageNumber()) {
				pageable.setPageNumber(totalPages);
			}
			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());
			return new Page<Acceptor>(query.getResultList(), total, pageable);
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
