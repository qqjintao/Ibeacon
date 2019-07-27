package com.ibeacon.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.OldManDao;
import com.ibeacon.entity.OldMan;
import com.ibeacon.entity.Login;
import com.ibeacon.service.OldManService;

@Service("oldManServiceImpl")
public class OldManServiceImpl extends BaseServiceImpl<OldMan, Long> implements OldManService{
	
	@Resource(name = "oldManDaoImpl")
	private OldManDao oldManDao;
	
	@Resource(name = "oldManDaoImpl")
	public void setBaseDao(OldManDao oldManDao) {
		super.setBaseDao(oldManDao);
	}
	
	public Page<OldMan> findOldManList(OldMan oldMan,Long id,Pageable pageable){
		return oldManDao.findOldManList(oldMan,id,pageable);
	}
	
	@Transactional(readOnly = true)
	public void addList(List<List<Object>> results,Login login){
		if(results!=null&&results.size()>0){
			for (int i = 0; i < results.size(); i++) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				List<Object> list = (List) results.get(i);
				OldMan oldMan=new OldMan();
				oldMan.setName(list.get(0)!=null?list.get(0).toString():"");
				oldMan.setEnglishName(list.get(1)!=null?list.get(1).toString():"");
				oldMan.setAge(list.get(2)!=null?list.get(2).toString():"");
				oldMan.setSex(list.get(3)!=null?list.get(3).toString():"");
				oldMan.setNationality(list.get(4)!=null?list.get(4).toString():"");
				oldMan.setNation(list.get(5)!=null?list.get(5).toString():"");
				oldMan.setPoliticsStatus(list.get(6)!=null?list.get(6).toString():"");
				oldMan.setIdentityCard(list.get(7)!=null?list.get(7).toString():"");
				oldMan.setPhysical(list.get(8)!=null?list.get(8).toString():"");
				oldMan.setPhone(list.get(9)!=null?list.get(9).toString():"");
				oldMan.setRelation(list.get(10)!=null?list.get(10).toString():"");
				oldMan.setProvince(list.get(11)!=null?list.get(11).toString():"");
				oldMan.setCity(list.get(12)!=null?list.get(12).toString():"");
				oldMan.setDistrict(list.get(13)!=null?list.get(13).toString():"");
				oldMan.setStreetAddress(list.get(14)!=null?list.get(14).toString():"");
				oldMan.setDomicileProvince(list.get(15)!=null?list.get(15).toString():"");
				oldMan.setDomicileCity(list.get(16)!=null?list.get(16).toString():"");
				oldMan.setDomicileDistrict(list.get(17)!=null?list.get(17).toString():"");
				oldMan.setDomicile(list.get(18)!=null?list.get(18).toString():"");
				oldMan.setAccount(list.get(19)!=null?list.get(19).toString():"");
				oldMan.setDoctor(list.get(20)!=null?list.get(20).toString():"");
				oldMan.setDoctorPhone(list.get(21)!=null?list.get(21).toString():"");
				oldMan.setNurse(list.get(22)!=null?list.get(22).toString():"");
				oldMan.setNursePhone(list.get(23)!=null?list.get(23).toString():"");
				oldMan.setEmergencyName(list.get(24)!=null?list.get(24).toString():"");
				oldMan.setEmergencyCall(list.get(25)!=null?list.get(25).toString():"");
				oldMan.setRemark(list.get(26)!=null?list.get(26).toString():"");
				Set<Login> logins = new HashSet<Login>();
				logins.add(login);
				oldMan.setLogin(logins);
				super.save(oldMan);
			}
		}else{
			throw new RuntimeException("导入的数据为空，请重新导入！");
		}
	}
}
