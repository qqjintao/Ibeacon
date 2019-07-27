package com.ibeacon.dao.impl;

import java.math.BigInteger;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.IbeaconDao;
import com.ibeacon.entity.Ibeacon;
import com.ibeacon.form.IbeaconContainer;

@Repository("ibeaconDaoImpl")
public class IbeaconDaoImpl extends BaseDaoImpl<Ibeacon, Long> implements IbeaconDao{

	public void save(Ibeacon ibeacon){
		this.persist(ibeacon);
	}
	
	public void update(Ibeacon ibeacon){
		this.merge(ibeacon);
	}
	
	public Integer IsExists(String UUID){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT count(1) FROM ibeacon WHERE ibeacon.uuid= :UUID ");
			String result=entityManager.createNativeQuery(sql.toString()).setFlushMode(FlushModeType.COMMIT)
			.setParameter("UUID",UUID).getResultList().get(0).toString();
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
	
	@SuppressWarnings("unchecked")
	public Page<IbeaconContainer> findIbeaconList(Ibeacon ibeacon,Long id,String oldman,Pageable pageable){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT ibeacon.*,old_man.id as oldman, old_man.`name` FROM ibeacon LEFT JOIN login_ibeacon ON login_ibeacon.ibeacon=ibeacon.id ");
			sql.append("LEFT JOIN  ibeacon_old_man ON  ibeacon_old_man.ibeacon=ibeacon.id ");
			sql.append("LEFT JOIN  old_man ON ibeacon_old_man.old_man=old_man.id WHERE 1=1 ");
			StringBuffer countSql=new StringBuffer();
			countSql.append("SELECT COUNT(1) FROM ibeacon LEFT JOIN login_ibeacon ON login_ibeacon.ibeacon=ibeacon.id ");
			countSql.append("LEFT JOIN  ibeacon_old_man ON  ibeacon_old_man.ibeacon=ibeacon.id ");
			countSql.append("LEFT JOIN  old_man ON ibeacon_old_man.old_man=old_man.id WHERE 1=1 ");
			if(id!=null){
				sql.append("and login_ibeacon.login = :id ");
				countSql.append("and login_ibeacon.login = :id ");
			}
			if(oldman!=null){
				sql.append("and old_man.`name` like :oldman ");
				countSql.append("and old_man.`name` like :oldman ");
			}
			if(ibeacon.getUUID()!=null){
				sql.append("and ibeacon.uuid = :uuid ");
				countSql.append("and ibeacon.uuid = :uuid ");
			}
			if(ibeacon.getMajor()!=null){
				sql.append("and ibeacon.major = :major ");
				countSql.append("and ibeacon.major = :major ");
			}
			if(ibeacon.getMinor()!=null){
				sql.append("and ibeacon.minor = :minor ");
				countSql.append("and ibeacon.minor = :minor ");
			}
			if(ibeacon.getMode()!=null){
				sql.append("and ibeacon.`mode` = :mode ");
				countSql.append("and ibeacon.`mode` = :mode ");
			}
			if(ibeacon.getElectric()!=null){
				sql.append("and ibeacon.electric = :electric ");
				countSql.append("and ibeacon.electric = :electric ");
			}
			if(ibeacon.getRemark()!=null){
				sql.append("and ibeacon.remark like :remark ");
				countSql.append("and ibeacon.remark like :remark ");
			}
			TypedQuery<IbeaconContainer> query = (TypedQuery<IbeaconContainer>) entityManager.createNativeQuery(sql.toString(),IbeaconContainer.class)
					.setFlushMode(FlushModeType.COMMIT);
			TypedQuery<BigInteger> countQuery=(TypedQuery<BigInteger>) entityManager.createNativeQuery(countSql.toString()).setFlushMode(FlushModeType.COMMIT);
			if(id!=null){
				query.setParameter("id", id);
				countQuery.setParameter("id", id);
			}
			if(oldman!=null){
				query.setParameter("oldman", "%"+oldman+"%");
				countQuery.setParameter("oldman", "%"+oldman+"%");
			}
			if(ibeacon.getUUID()!=null){
				query.setParameter("uuid", ibeacon.getUUID());
				countQuery.setParameter("uuid", ibeacon.getUUID());
			}
			if(ibeacon.getMajor()!=null){
				query.setParameter("major", ibeacon.getMajor());
				countQuery.setParameter("major", ibeacon.getMajor());
			}
			if(ibeacon.getMinor()!=null){
				query.setParameter("minor", ibeacon.getMinor());
				countQuery.setParameter("minor", ibeacon.getMinor());
			}
			if(ibeacon.getMode()!=null){
				query.setParameter("mode", ibeacon.getMode());
				countQuery.setParameter("mode", ibeacon.getMode());
			}
			if(ibeacon.getElectric()!=null){
				query.setParameter("electric", ibeacon.getElectric());
				countQuery.setParameter("electric", ibeacon.getElectric());
			}
			if(ibeacon.getRemark()!=null){
				query.setParameter("remark", "%"+ibeacon.getRemark()+"%");
				countQuery.setParameter("remark", "%"+ibeacon.getRemark()+"%");
			}
			int total = Integer.valueOf(countQuery.getResultList().get(0).toString());
			int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
			if (totalPages < pageable.getPageNumber()) {
				pageable.setPageNumber(totalPages);
			}
			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());
			return new Page<IbeaconContainer>(query.getResultList(), total, pageable);
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
