package com.ibeacon.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.ibeacon.dao.FenceDao;
import com.ibeacon.entity.Fence;

@Repository("fenceDaoImpl")
public class FenceDaoImpl  extends BaseDaoImpl<Fence, Long> implements FenceDao{
	
	public Fence findByLoginId(Long id){
		try {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT fence.* FROM fence LEFT JOIN login_fence on fence.id=login_fence.fence WHERE login_fence.login= :id ");
		return (Fence) entityManager.createNativeQuery(sql.toString(),Fence.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("id",id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
