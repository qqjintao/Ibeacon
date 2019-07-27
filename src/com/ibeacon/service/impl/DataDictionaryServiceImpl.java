package com.ibeacon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibeacon.dao.DataDictionaryDao;
import com.ibeacon.entity.DataDictionary;
import com.ibeacon.service.DataDictionaryService;

@Service("dataDictionaryServiceImpl")
public class DataDictionaryServiceImpl extends BaseServiceImpl<DataDictionary, Long> implements DataDictionaryService{
	
	@Resource(name = "dataDictionaryDaoImpl")
	private DataDictionaryDao dataDictionaryDao;
	
	@Resource(name = "dataDictionaryDaoImpl")
	public void setBaseDao(DataDictionaryDao dataDictionaryDao) {
		super.setBaseDao(dataDictionaryDao);
	}
	
	public Long findIdByName(String name){
		return dataDictionaryDao.findIdByName(name);
	}
	
	public List<DataDictionary>  findDataDictionaryList(Long parentId){
		return dataDictionaryDao.findDataDictionaryList(parentId);
	}
	
	public List<DataDictionary> findList(){
		return dataDictionaryDao.findList();
	}
}
