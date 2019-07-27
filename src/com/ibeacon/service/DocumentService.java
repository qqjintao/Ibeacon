package com.ibeacon.service;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Document;

public interface DocumentService extends BaseService<Document, Long>{
	
	/**
	 * 分页查询Document列表
	 */
	public Page<Document> findDocumentList(Document document,Long id,Pageable pageable);
	
}
