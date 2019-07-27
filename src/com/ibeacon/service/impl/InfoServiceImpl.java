package com.ibeacon.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.InfoDao;
import com.ibeacon.dao.LoginDao;
import com.ibeacon.entity.Info;
import com.ibeacon.entity.Login;
import com.ibeacon.service.InfoService;

@Service("infoServiceImpl")
public class InfoServiceImpl extends BaseServiceImpl<Info, Long> implements InfoService{
	
	@Resource(name = "infoDaoImpl")
	private InfoDao infoDao;
	
	@Resource(name = "loginDaoImpl")
	private LoginDao loginDao;
	
	@Resource(name = "infoDaoImpl")
	public void setBaseDao(InfoDao infoDao) {
		super.setBaseDao(infoDao);
	}
	
	public Page<Info> findInfoList(Info info,Long id,Pageable pageable){
		return infoDao.findInfoList(info,id,pageable);
	}
	

	@Transactional(readOnly = true)
	public void addList(List<List<Object>> results,Login login){
		if(results!=null&&results.size()>0){
			for (int i = 0; i < results.size(); i++) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				List<Object> list = (List) results.get(i);
				Info info=new Info();
				info.setIdCardName(list.get(0)!=null?list.get(0).toString():"");
				info.setEnglishName(list.get(1)!=null?list.get(1).toString():"");
				info.setAge(list.get(2)!=null?list.get(2).toString():"");
				info.setSex(list.get(3)!=null?list.get(3).toString():"");
				info.setBirthplace(list.get(4)!=null?list.get(4).toString():"");
				info.setDateOfBirth(list.get(5)!=null?list.get(5).toString():"");
				info.setIdCard(list.get(6)!=null?list.get(6).toString():"");
				info.setNation(list.get(7)!=null?list.get(7).toString():"");
				info.setPoliticsStatus(list.get(8)!=null?list.get(8).toString():"");
				info.setMaritalStatus(list.get(9)!=null?list.get(9).toString():"");
				info.setProvince(list.get(10)!=null?list.get(10).toString():"");
				info.setCity(list.get(11)!=null?list.get(11).toString():"");
				info.setDistrict(list.get(12)!=null?list.get(12).toString():"");
				info.setStreetAddress(list.get(13)!=null?list.get(13).toString():"");
				info.setDomicileProvince(list.get(14)!=null?list.get(14).toString():"");
				info.setDomicileCity(list.get(15)!=null?list.get(15).toString():"");
				info.setDomicileDistrict(list.get(16)!=null?list.get(16).toString():"");
				info.setDomicile(list.get(17)!=null?list.get(17).toString():"");
				info.setMailbox(list.get(18)!=null?list.get(18).toString():"");
				info.setPhone(list.get(19)!=null?list.get(19).toString():"");
				info.setHomePhone(list.get(20)!=null?list.get(20).toString():"");
				info.setOfficePhone(list.get(21)!=null?list.get(21).toString():"");
				info.setRemark(list.get(22)!=null?list.get(22).toString():"");
				Set<Login> logins = new HashSet<Login>();
				logins.add(login);
				info.setLogin(logins);
				super.save(info);
			}
		}else{
			throw new RuntimeException("导入的数据为空，请重新导入！");
		}
	}
}
