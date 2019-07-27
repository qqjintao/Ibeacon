package com.ibeacon.dao.impl;

import java.math.BigInteger;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.IBeaconInputDao;
import com.ibeacon.entity.IBeaconInput;
import com.ibeacon.form.IBeaconVo;

@Repository("iBeaconInputDaoImpl")
public class IBeaconInputDaoImpl extends BaseDaoImpl<IBeaconInput, Long> implements IBeaconInputDao{
	
	@SuppressWarnings("unchecked")
	public Page<IBeaconInput> findIBeaconInputList(IBeaconInput iBeaconInput, Pageable pageable){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT * FROM ibeacon_input WHERE 1=1 ");
			StringBuffer countSql=new StringBuffer();
			countSql.append("SELECT COUNT(1) FROM ibeacon_input WHERE 1=1 ");	
			if(iBeaconInput.getId()!=null){
				sql.append("AND ibeacon_input.id= :id ");
				countSql.append("AND ibeacon_input.id= :id ");
			}
			if(iBeaconInput.getUUID()!=null){
				sql.append("AND ibeacon_input.uuid= :uuid ");
				countSql.append("AND ibeacon_input.uuid= :uuid ");
			}
			if(iBeaconInput.getMajor()!=null){
				sql.append("AND ibeacon_input.major= :major ");
				countSql.append("AND ibeacon_input.major= :major ");
			}
			if(iBeaconInput.getMinor()!=null){
				sql.append("AND ibeacon_input.minor= :minor ");
				countSql.append("AND ibeacon_input.minor= :minor ");
			}
			if(iBeaconInput.getName()!=null){
				sql.append("AND ibeacon_input.`name` LIKE :name ");
				countSql.append("AND ibeacon_input.`name` LIKE :name ");
			}
			if(iBeaconInput.getTime()!=null){
				sql.append("AND ibeacon_input.time= :time ");
				countSql.append("AND ibeacon_input.time= :time ");
			}
			
			TypedQuery<IBeaconInput> query = (TypedQuery<IBeaconInput>) entityManager.createNativeQuery(sql.toString(),IBeaconInput.class)
					.setFlushMode(FlushModeType.COMMIT);
			TypedQuery<BigInteger> countQuery=(TypedQuery<BigInteger>) entityManager.createNativeQuery(countSql.toString()).setFlushMode(FlushModeType.COMMIT);
			if(iBeaconInput.getId()!=null){
				query.setParameter("id", iBeaconInput.getId());
				countQuery.setParameter("id", iBeaconInput.getId());
			}
			if(iBeaconInput.getUUID()!=null){
				query.setParameter("uuid", iBeaconInput.getUUID());
				countQuery.setParameter("uuid", iBeaconInput.getUUID());
			}
			if(iBeaconInput.getMajor()!=null){
				query.setParameter("major", iBeaconInput.getMajor());
				countQuery.setParameter("major", iBeaconInput.getMajor());
			}
			if(iBeaconInput.getMinor()!=null){
				query.setParameter("minor", iBeaconInput.getMinor());
				countQuery.setParameter("minor", iBeaconInput.getMinor());
			}
			if(iBeaconInput.getName()!=null){;
				query.setParameter("name", "%"+iBeaconInput.getName()+"%");
				countQuery.setParameter("name", "%"+iBeaconInput.getName()+"%");
			}
			if(iBeaconInput.getTime()!=null){
				query.setParameter("time", iBeaconInput.getTime());
				countQuery.setParameter("time", iBeaconInput.getTime());
			}
			int total = Integer.valueOf(countQuery.getResultList().get(0).toString());
			int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
			if (totalPages < pageable.getPageNumber()) {
				pageable.setPageNumber(totalPages);
			}
			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());
			return new Page<IBeaconInput>(query.getResultList(), total, pageable);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Page<IBeaconVo> findIBeaconInput(IBeaconInput iBeaconInput,String oldMan,String acceptorName,String address,Long LoginId,Pageable pageable){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT ibeacon_input.id as id,ibeacon_input.create_date,ibeacon_input.create_name,ibeacon_input.modify_date, ");
			sql.append("ibeacon_input.modify_name,ibeacon_input.uuid as uuid,ibeacon_input.major as major, ");
			sql.append("ibeacon_input.minor as minor,ibeacon_input.time as time,old_man.`name` as old_man,acceptor.`name` as `name`, ");
			sql.append("acceptor.address as address,acceptor.alarm_time as alarm_time,acceptor.alarm_voice as alarm_voice, ");
			sql.append("acceptor.phone as phone  FROM ibeacon_input ");
			sql.append("LEFT JOIN ibeacon on ibeacon.uuid=ibeacon_input.uuid ");
			sql.append("LEFT JOIN ibeacon_old_man on ibeacon_old_man.ibeacon = ibeacon.id ");
			sql.append("LEFT JOIN old_man on ibeacon_old_man.old_man=old_man.id ");
			sql.append("LEFT JOIN login_ibeacon on login_ibeacon.ibeacon=ibeacon.id ");
			sql.append("LEFT JOIN acceptor on acceptor.`name`=ibeacon_input.`name` WHERE 1=1 ");
			StringBuffer countSql=new StringBuffer();
			countSql.append("SELECT COUNT(1) FROM ibeacon_input ");
			countSql.append("LEFT JOIN ibeacon on ibeacon.uuid=ibeacon_input.uuid ");
			countSql.append("LEFT JOIN ibeacon_old_man on ibeacon_old_man.ibeacon = ibeacon.id ");
			countSql.append("LEFT JOIN old_man on ibeacon_old_man.old_man=old_man.id ");
			countSql.append("LEFT JOIN login_ibeacon on login_ibeacon.ibeacon=ibeacon.id ");
			countSql.append("LEFT JOIN acceptor on acceptor.`name`=ibeacon_input.`name` WHERE 1=1 ");
			if(iBeaconInput.getId()!=null){
				sql.append("AND ibeacon_input.id= :id ");
				countSql.append("AND ibeacon_input.id= :id ");
			}
			if(iBeaconInput.getUUID()!=null){
				sql.append("AND ibeacon_input.uuid= :uuid ");
				countSql.append("AND ibeacon_input.uuid= :uuid ");
			}
			if(iBeaconInput.getMajor()!=null){
				sql.append("AND ibeacon_input.major= :major ");
				countSql.append("AND ibeacon_input.major= :major ");
			}
			if(iBeaconInput.getMinor()!=null){
				sql.append("AND ibeacon_input.minor= :minor ");
				countSql.append("AND ibeacon_input.minor= :minor ");
			}
			if(iBeaconInput.getName()!=null){
				sql.append("AND ibeacon_input.`name` LIKE :name ");
				countSql.append("AND ibeacon_input.`name` LIKE :name ");
			}
			if(iBeaconInput.getTime()!=null){
				sql.append("AND ibeacon_input.time= :time ");
				countSql.append("AND ibeacon_input.time= :time ");
			}
			if(oldMan!=null){
				sql.append("AND old_man.`name` LIKE  :oldMan ");
				countSql.append("AND old_man.`name` LIKE :oldMan ");
			}
			if(acceptorName!=null){
				sql.append("AND acceptor.`name` LIKE :acceptorName ");
				countSql.append("AND acceptor.`name` LIKE :acceptorName ");
			}
			if(address!=null){
				sql.append("AND acceptor.`address` LIKE :address ");
				countSql.append("AND acceptor.`address` LIKE :address ");
			}
			if(LoginId!=null){
				sql.append("AND login_ibeacon.login = :LoginId ");
				countSql.append("AND login_ibeacon.login = :LoginId ");
			}
			TypedQuery<IBeaconVo> query = (TypedQuery<IBeaconVo>) entityManager.createNativeQuery(sql.toString(),IBeaconVo.class)
					.setFlushMode(FlushModeType.COMMIT);
			TypedQuery<BigInteger> countQuery=(TypedQuery<BigInteger>) entityManager.createNativeQuery(countSql.toString()).setFlushMode(FlushModeType.COMMIT);
			if(iBeaconInput.getId()!=null){
				query.setParameter("id", iBeaconInput.getId());
				countQuery.setParameter("id", iBeaconInput.getId());
			}
			if(iBeaconInput.getUUID()!=null){
				query.setParameter("uuid", iBeaconInput.getUUID());
				countQuery.setParameter("uuid", iBeaconInput.getUUID());
			}
			if(iBeaconInput.getMajor()!=null){
				query.setParameter("major", iBeaconInput.getMajor());
				countQuery.setParameter("major", iBeaconInput.getMajor());
			}
			if(iBeaconInput.getMinor()!=null){
				query.setParameter("minor", iBeaconInput.getMinor());
				countQuery.setParameter("minor", iBeaconInput.getMinor());
			}
			if(iBeaconInput.getName()!=null){;
				query.setParameter("name", "%"+iBeaconInput.getName()+"%");
				countQuery.setParameter("name", "%"+iBeaconInput.getName()+"%");
			}
			if(iBeaconInput.getTime()!=null){
				query.setParameter("time", iBeaconInput.getTime());
				countQuery.setParameter("time", iBeaconInput.getTime());
			}
			if(oldMan!=null){
				query.setParameter("oldMan", "%"+oldMan+"%");
				countQuery.setParameter("oldMan", "%"+oldMan+"%");
			}
			if(acceptorName!=null){
				query.setParameter("acceptorName", "%"+acceptorName+"%");
				countQuery.setParameter("acceptorName", "%"+acceptorName+"%");
			}
			
			if(address!=null){
				query.setParameter("address", "%"+address+"%");
				countQuery.setParameter("address", "%"+address+"%");
			}
			if(LoginId!=null){
				query.setParameter("LoginId", LoginId);
				countQuery.setParameter("LoginId", LoginId);
			}
			int total = Integer.valueOf(countQuery.getResultList().get(0).toString());
			int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
			if (totalPages < pageable.getPageNumber()) {
				pageable.setPageNumber(totalPages);
			}
			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());
			return new Page<IBeaconVo>(query.getResultList(), total, pageable);
		} catch (NoResultException e) {
			return null;
		}
	}
}
