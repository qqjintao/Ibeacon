package com.ibeacon.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.ibeacon.dao.DataDictionaryDao;
import com.ibeacon.entity.DataDictionary;

@Repository("dataDictionaryDaoImpl")
public class DataDictionaryDaoImpl extends BaseDaoImpl<DataDictionary, Long> implements DataDictionaryDao {
	
	public Long findIdByName(String name){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT dataDictionary.id FROM DataDictionary dataDictionary WHERE dataDictionary.name = :name ");
			return entityManager.createQuery(sql.toString(),Long.class).setFlushMode(FlushModeType.COMMIT)
			.setParameter("name",name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<DataDictionary> findDataDictionaryList(Long parentId){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT dataDictionary FROM DataDictionary dataDictionary WHERE dataDictionary.parentId = :parentId ");
			return entityManager.createQuery(sql.toString(),DataDictionary.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("parentId", parentId).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<DataDictionary> findList(){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT dataDictionary FROM DataDictionary dataDictionary ");
			return entityManager.createQuery(sql.toString(),DataDictionary.class).setFlushMode(FlushModeType.COMMIT).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
