package com.ibeacon.dao;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Document;

public interface DocumentDao extends BaseDao<Document,Long>{
	
	public Page<Document> findDocumentList(Document document,Long id,Pageable pageable);

}
