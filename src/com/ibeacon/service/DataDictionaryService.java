package com.ibeacon.service;

import java.util.List;

import com.ibeacon.entity.DataDictionary;

public interface DataDictionaryService  extends BaseService<DataDictionary, Long>{
	
	public Long findIdByName(String name);
	
	public List<DataDictionary>  findDataDictionaryList(Long parentId);
	
	public List<DataDictionary> findList();
}
