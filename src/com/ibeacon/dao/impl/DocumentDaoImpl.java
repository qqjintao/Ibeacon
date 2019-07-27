package com.ibeacon.dao.impl;

import java.math.BigInteger;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.DocumentDao;
import com.ibeacon.entity.Document;

@Repository("documentDaoImpl")
public class DocumentDaoImpl extends BaseDaoImpl<Document, Long> implements DocumentDao{
	public void save(Document document){
		this.persist(document);
	}
	
	public void update(Document document){
		this.merge(document);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Document> findDocumentList(Document document,Long id,Pageable pageable){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT document.* FROM document JOIN login_document ON login_document.document=document.id WHERE 1=1 ");
			StringBuffer countSql=new StringBuffer();
			countSql.append("SELECT COUNT(1) FROM document JOIN login_document ON login_document.document=document.id WHERE 1=1 ");	
			if(id!=null){
				sql.append("and login_document.login = :id ");
				countSql.append("and login_document.login = :id ");
			}
			if(document.getName()!=null){
				sql.append("and document.`name` like :name ");
				countSql.append("and document.`name` like :name ");
			}
			if(document.getLoadAddress()!=null){
				sql.append("and document.load_address like :load_address ");
				countSql.append("and document.load_address like :load_address ");
			}
			if(document.getRemark()!=null){
				sql.append("and document.remark like :remark ");
				countSql.append("and document.remark like :remark ");
			}
			TypedQuery<Document> query = (TypedQuery<Document>) entityManager.createNativeQuery(sql.toString(),Document.class)
					.setFlushMode(FlushModeType.COMMIT);
			TypedQuery<BigInteger> countQuery=(TypedQuery<BigInteger>) entityManager.createNativeQuery(countSql.toString()).setFlushMode(FlushModeType.COMMIT);
			if(id!=null){
				query.setParameter("id", id);
				countQuery.setParameter("id", id);
			}
			if(document.getName()!=null){
				query.setParameter("name", "%"+document.getName()+"%");
				countQuery.setParameter("name", "%"+document.getName()+"%");
			}
			if(document.getLoadAddress()!=null){
				query.setParameter("load_address", "%"+document.getLoadAddress()+"%");
				countQuery.setParameter("load_address", "%"+document.getLoadAddress()+"%");
			}
			if(document.getRemark()!=null){
				query.setParameter("remark", "%"+document.getRemark()+"%");
				countQuery.setParameter("remark", "%"+document.getRemark()+"%");
			}
			int total = Integer.valueOf(countQuery.getResultList().get(0).toString());
			int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
			if (totalPages < pageable.getPageNumber()) {
				pageable.setPageNumber(totalPages);
			}
			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());
			return new Page<Document>(query.getResultList(), total, pageable);
		} catch (NoResultException e) {
			return null;
		}
	}
}
