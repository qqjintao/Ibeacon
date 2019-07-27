package com.ibeacon.dao;

import java.util.List;

import com.ibeacon.entity.DataDictionary;

public interface DataDictionaryDao extends BaseDao<DataDictionary,Long>{
	
	public Long findIdByName(String name);
	
	public List<DataDictionary>  findDataDictionaryList(Long parentId);
	
	public List<DataDictionary> findList();
}
