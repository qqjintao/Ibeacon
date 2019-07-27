package com.ibeacon.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.DocumentDao;
import com.ibeacon.entity.Document;
import com.ibeacon.service.DocumentService;

@Service("documentServiceImpl")
public class DocumentServiceImpl extends BaseServiceImpl<Document, Long> implements DocumentService{

	@Resource(name = "documentDaoImpl")
	private DocumentDao documentDao;
	
	@Resource(name = "documentDaoImpl")
	public void setBaseDao(DocumentDao documentDao) {
		super.setBaseDao(documentDao);
	}
	
	public Page<Document> findDocumentList(Document document,Long id,Pageable pageable){
		return documentDao.findDocumentList(document,id,pageable);
	}
}
