package com.ibeacon.dao.impl;

import java.math.BigInteger;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.OldManDao;
import com.ibeacon.entity.OldMan;

@Repository("oldManDaoImpl")
public class OldManDaoImpl extends BaseDaoImpl<OldMan, Long> implements OldManDao{
	
	public void save(OldMan oldMan){
		this.persist(oldMan);
	}
	
	public void update(OldMan oldMan){
		this.merge(oldMan);
	}
	
	@SuppressWarnings("unchecked")
	public Page<OldMan> findOldManList(OldMan oldMan, Long id, Pageable pageable){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT old_man.* FROM old_man JOIN login_old_man ON login_old_man.old_man=old_man.id WHERE 1=1 ");
			StringBuffer countSql=new StringBuffer();
			countSql.append("SELECT COUNT(1) FROM old_man JOIN login_old_man ON login_old_man.old_man=old_man.id WHERE 1=1 ");	
			if(id!=null){
				sql.append("and login_old_man.login = :id ");
				countSql.append("and login_old_man.login = :id ");
			}
			if(oldMan.getId()!=null){
				sql.append("AND old_man.`id` = :oldman ");
				countSql.append("AND old_man.`id` = :oldman ");
			}
			if(oldMan.getName()!=null){
				sql.append("AND old_man.`name` LIKE :name ");
				countSql.append("AND old_man.`name` LIKE :name ");
			}
			if(oldMan.getAge()!=null){
				sql.append("AND old_man.age = :age ");
				countSql.append("AND old_man.age = :age ");
			}
			if(oldMan.getAge()!=null){
				sql.append("AND old_man.english_name LIKE :english_name ");
				countSql.append("AND old_man.english_name LIKE :english_name ");
			}
			if(oldMan.getNationality()!=null){
				sql.append("AND old_man.nationality LIKE :nationality ");
				countSql.append("AND old_man.nationality LIKE :nationality ");
			}
			if(oldMan.getNation()!=null){
				sql.append("AND old_man.nation LIKE :nation ");
				countSql.append("AND old_man.nation LIKE :nation ");
			}
			if(oldMan.getPoliticsStatus()!=null){
				sql.append("AND old_man.politics_status LIKE :politics_status ");
				countSql.append("AND old_man.politics_status LIKE :politics_status ");
			}
			if(oldMan.getSex()!=null){
				sql.append("AND old_man.sex =  :sex ");
				countSql.append("AND old_man.sex = :sex	");
			}
			if(oldMan.getProvince()!=null){
				sql.append("AND old_man.province = :province ");
				countSql.append("AND old_man.province = :province ");
			}
			if(oldMan.getCity()!=null){
				sql.append("AND old_man.city = :city ");
				countSql.append("AND old_man.city = :city ");
			}
			if(oldMan.getDistrict()!=null){
				sql.append("AND old_man.district = :district ");
				countSql.append("AND old_man.district = :district ");
			}
			if(oldMan.getStreetAddress()!=null){
				sql.append("AND old_man.street_address LIKE :street_address ");
				countSql.append("AND old_man.street_address LIKE :street_address ");
			}
			if(oldMan.getDomicileProvince()!=null){
				sql.append("AND old_man.domicile_province = :domicile_province ");
				countSql.append("AND old_man.domicile_province = :domicile_province ");
			}
			if(oldMan.getDomicileCity()!=null){
				sql.append("AND old_man.domicile_city = :domicile_city ");
				countSql.append("AND old_man.domicile_city = :domicile_city ");
			}
			if(oldMan.getDomicileDistrict()!=null){
				sql.append("AND old_man.domicile_district =  :domicile_district ");
				countSql.append("AND old_man.domicile_district = :domicile_district ");
			}
			if(oldMan.getDomicile()!=null){
				sql.append("AND old_man.domicile LIKE :domicile ");
				countSql.append("AND old_man.domicile LIKE :domicile ");
			}
			if(oldMan.getIdentityCard()!=null){
				sql.append("AND old_man.identity_card = :identity_card ");
				countSql.append("AND old_man.identity_card = :identity_card ");
			}
			if(oldMan.getPhysical()!=null){
				sql.append("AND old_man.physical = :physical ");
				countSql.append("AND old_man.physical = :physical ");
			}
			if(oldMan.getPhone()!=null){
				sql.append("AND old_man.phone = :phone ");
				countSql.append("AND old_man.phone = :phone ");
			}
			if(oldMan.getRelation()!=null){
				sql.append("AND old_man.relation like :relation ");
				countSql.append("AND old_man.relation like :relation ");
			}
			if(oldMan.getAccount()!=null){
				sql.append("AND old_man.account = :account ");
				countSql.append("AND old_man.account = :account ");
			}
			if(oldMan.getDoctor()!=null){
				sql.append("AND old_man.doctor LIKE :doctor ");
				countSql.append("AND old_man.doctor LIKE :doctor ");
			}
			if(oldMan.getDoctorPhone()!=null){
				sql.append("AND old_man.doctor_phone = :doctor_phone ");
				countSql.append("AND old_man.doctor_phone = :doctor_phone ");
			}
			if(oldMan.getNurse()!=null){
				sql.append("AND old_man.nurse LIKE :nurse ");
				countSql.append("AND old_man.nurse LIKE :nurse ");
			}
			if(oldMan.getNursePhone()!=null){
				sql.append("AND old_man.nurse_phone = :nurse_phone ");
				countSql.append("AND old_man.nurse_phone = :nurse_phone ");
			}
			if(oldMan.getEmergencyName()!=null){
				sql.append("AND old_man.emergency_name LIKE :emergency_name ");
				countSql.append("AND old_man.emergency_name LIKE :emergency_name ");
			}
			if(oldMan.getEmergencyCall()!=null){
				sql.append("AND old_man.emergency_call = :emergency_call ");
				countSql.append("AND old_man.emergency_call = :emergency_call ");
			}
			if(oldMan.getRemark()!=null){
				sql.append("AND old_man.remark like :remark ");
				countSql.append("AND old_man.remark like :remark ");
			}
			
			TypedQuery<OldMan> query = (TypedQuery<OldMan>) entityManager.createNativeQuery(sql.toString(),OldMan.class)
					.setFlushMode(FlushModeType.COMMIT);
			TypedQuery<BigInteger> countQuery=(TypedQuery<BigInteger>) entityManager.createNativeQuery(countSql.toString()).setFlushMode(FlushModeType.COMMIT);
			if(id!=null){
				query.setParameter("id", id);
				countQuery.setParameter("id", id);
			}
			if(oldMan.getId()!=null){
				query.setParameter("oldman", oldMan.getId());
				countQuery.setParameter("oldman", oldMan.getId());
			}
			if(oldMan.getName()!=null){;
				query.setParameter("name", "%"+oldMan.getName()+"%");
				countQuery.setParameter("name", "%"+oldMan.getName()+"%");
			}
			if(oldMan.getAge()!=null){
				query.setParameter("age", oldMan.getAge());
				countQuery.setParameter("age", oldMan.getAge());
			}
			if(oldMan.getEnglishName()!=null){
				query.setParameter("english_name", "%"+oldMan.getEnglishName()+"%");
				countQuery.setParameter("english_name", "%"+oldMan.getEnglishName()+"%");
			}
			if(oldMan.getNationality()!=null){
				query.setParameter("nationality", "%"+oldMan.getNationality()+"%");
				countQuery.setParameter("nationality", "%"+oldMan.getNationality()+"%");
			}
			if(oldMan.getNation()!=null){
				query.setParameter("nation", "%"+oldMan.getNation()+"%");
				countQuery.setParameter("nation", "%"+oldMan.getNation()+"%");
			}
			if(oldMan.getPoliticsStatus()!=null){
				query.setParameter("politics_status", "%"+oldMan.getPoliticsStatus()+"%");
				countQuery.setParameter("politics_status", "%"+oldMan.getPoliticsStatus()+"%");
			}
			if(oldMan.getSex()!=null){
				query.setParameter("sex", oldMan.getSex());
				countQuery.setParameter("sex", oldMan.getSex());
			}
			if(oldMan.getProvince()!=null){
				query.setParameter("province", oldMan.getProvince());
				countQuery.setParameter("province", oldMan.getProvince());
			}
			if(oldMan.getCity()!=null){
				query.setParameter("city", oldMan.getCity());
				countQuery.setParameter("city", oldMan.getCity());
			}
			if(oldMan.getDistrict()!=null){
				query.setParameter("district", oldMan.getDistrict());
				countQuery.setParameter("district", oldMan.getDistrict());
			}
			if(oldMan.getStreetAddress()!=null){
				query.setParameter("street_address", "%"+oldMan.getStreetAddress()+"%");
				countQuery.setParameter("street_address", "%"+oldMan.getStreetAddress()+"%");
			}
			if(oldMan.getDomicileProvince()!=null){
				query.setParameter("domicile_province", oldMan.getDomicileProvince());
				countQuery.setParameter("domicile_province", oldMan.getDomicileProvince());
			}
			if(oldMan.getDomicileCity()!=null){
				query.setParameter("domicile_city", oldMan.getDomicileCity());
				countQuery.setParameter("domicile_city", oldMan.getDomicileCity());
			}
			if(oldMan.getDomicileDistrict()!=null){
				query.setParameter("domicile_district", oldMan.getDomicileDistrict());
				countQuery.setParameter("domicile_district", oldMan.getDomicileDistrict());
			}
			if(oldMan.getDomicile()!=null){
				query.setParameter("domicile", "%"+oldMan.getDomicile()+"%");
				countQuery.setParameter("domicile", "%"+oldMan.getDomicile()+"%");
			}
			if(oldMan.getIdentityCard()!=null){
				query.setParameter("identity_card", oldMan.getIdentityCard());
				countQuery.setParameter("identity_card", oldMan.getIdentityCard());
			}
			if(oldMan.getPhysical()!=null){
				query.setParameter("physical", oldMan.getPhysical());
				countQuery.setParameter("physical", oldMan.getPhysical());
			}
			if(oldMan.getPhone()!=null){
				query.setParameter("phone", oldMan.getPhone());
				countQuery.setParameter("phone", oldMan.getPhone());
			}
			if(oldMan.getRelation()!=null){
				query.setParameter("relation", "%"+oldMan.getRelation()+"%");
				countQuery.setParameter("relation", "%"+oldMan.getRelation()+"%");
			}
			if(oldMan.getAccount()!=null){
				query.setParameter("account", oldMan.getAccount());
				countQuery.setParameter("account", oldMan.getAccount());
			}
			if(oldMan.getDoctor()!=null){
				query.setParameter("doctor", "%"+oldMan.getDoctor()+"%");
				countQuery.setParameter("doctor", "%"+oldMan.getDoctor()+"%");
			}
			if(oldMan.getDoctorPhone()!=null){
				query.setParameter("doctor_phone", oldMan.getDoctorPhone());
				countQuery.setParameter("doctor_phone", oldMan.getDoctorPhone());
			}
			if(oldMan.getNurse()!=null){
				query.setParameter("nurse", "%"+oldMan.getNurse()+"%");
				countQuery.setParameter("nurse", "%"+oldMan.getNurse()+"%");
			}
			if(oldMan.getNursePhone()!=null){
				query.setParameter("nurse_phone", oldMan.getNursePhone());
				countQuery.setParameter("nurse_phone", oldMan.getNursePhone());
			}	
			if(oldMan.getEmergencyName()!=null){
				query.setParameter("emergency_name", "%"+oldMan.getEmergencyName()+"%");
				countQuery.setParameter("emergency_name", "%"+oldMan.getEmergencyName()+"%");
			}
			if(oldMan.getEmergencyCall()!=null){
				query.setParameter("emergency_call", oldMan.getEmergencyCall());
				countQuery.setParameter("emergency_call", oldMan.getEmergencyCall());
			}
			if(oldMan.getRemark()!=null){
				query.setParameter("remark", "%"+oldMan.getRemark()+"%");
				countQuery.setParameter("remark", "%"+oldMan.getRemark()+"%");
			}
			int total = Integer.valueOf(countQuery.getResultList().get(0).toString());
			int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
			if (totalPages < pageable.getPageNumber()) {
				pageable.setPageNumber(totalPages);
			}
			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());
			return new Page<OldMan>(query.getResultList(), total, pageable);
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
