package com.ibeacon.dao.impl;

import java.math.BigInteger;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.dao.InfoDao;
import com.ibeacon.entity.Info;


@Repository("infoDaoImpl")
public class InfoDaoImpl extends BaseDaoImpl<Info, Long> implements InfoDao{
	
	public void save(Info info){
		this.persist(info);
	}
	
	public void update(Info info){
		this.merge(info);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Info> findInfoList(Info info,Long id,Pageable pageable){
		try {
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT info.* FROM info JOIN login_info ON login_info.info=info.id WHERE 1=1 ");
			StringBuffer countSql=new StringBuffer();
			countSql.append("SELECT COUNT(1) FROM info JOIN login_info ON login_info.info=info.id WHERE 1=1 ");	
			if(id!=null){
				sql.append("and login_info.login = :id ");
				countSql.append("and login_info.login = :id ");
			}
			if(info.getIdCardName()!=null){
				sql.append("and info.id_card_name like :id_card_name ");
				countSql.append("and info.id_card_name like :id_card_name ");
			}
			if(info.getEnglishName()!=null){
				sql.append("and info.english_name like :english_name ");
				countSql.append("and info.english_name like :english_name ");
			}
			if(info.getAge()!=null){
				sql.append("and info.age = :age ");
				countSql.append("and info.age = :age ");
			}
			if(info.getSex()!=null){
				sql.append("and info.sex = :sex ");
				countSql.append("and info.sex = :sex ");
			}
			if(info.getBirthplace()!=null){
				sql.append("and info.birthplace like :birthplace ");
				countSql.append("and info.birthplace like :birthplace ");
			}
			if(info.getDateOfBirth()!=null){
				sql.append("and info.date_of_birth = :date_of_birth ");
				countSql.append("and info.date_of_birth = :date_of_birth ");
			}
			if(info.getNationality()!=null){
				sql.append("and info.nationality like :nationality ");
				countSql.append("and info.nationality like :nationality ");
			}
			if(info.getIdCard()!=null){
				sql.append("and info.id_card = :id_card ");
				countSql.append("and info.id_card = :id_card ");
			}
			if(info.getNation()!=null){
				sql.append("and info.nation like :nation ");
				countSql.append("and info.nation like :nation ");
			}
			if(info.getPoliticsStatus()!=null){
				sql.append("and info.politics_status like :politics_status ");
				countSql.append("and info.politics_status like :politics_status ");
			}
			if(info.getMaritalStatus()!=null){
				sql.append("and info.marital_status = :marital_status ");
				countSql.append("and info.marital_status = :marital_status ");
			}
			if(info.getProvince()!=null){
				sql.append("and info.province = :province ");
				countSql.append("and info.province = :province ");
			}
			if(info.getCity()!=null){
				sql.append("and info.city = :city ");
				countSql.append("and info.city = :city ");
			}
			if(info.getDistrict()!=null){
				sql.append("and info.district = :district ");
				countSql.append("and info.district = :district ");
			}
			if(info.getStreetAddress()!=null){
				sql.append("and info.street_address like :street_address ");
				countSql.append("and info.street_address like :street_address ");
			}
			if(info.getDomicileProvince()!=null){
				sql.append("and info.domicile_province = :domicile_province ");
				countSql.append("and info.domicile_province = :domicile_province ");
			}
			if(info.getDomicileCity()!=null){
				sql.append("and info.domicile_city = :domicile_city ");
				countSql.append("and info.domicile_city = :domicile_city ");
			}
			if(info.getDomicileDistrict()!=null){
				sql.append("and info.domicile_district = :domicile_district ");
				countSql.append("and info.domicile_district = :domicile_district ");
			}
			if(info.getDistrict()!=null){
				sql.append("and info.domicile like :domicile ");
				countSql.append("and info.domicile like :domicile ");
			}
			if(info.getMailbox()!=null){
				sql.append("and info.mailbox = :mailbox ");
				countSql.append("and info.mailbox = :mailbox ");
			}
			if(info.getPhone()!=null){
				sql.append("and info.phone = :phone ");
				countSql.append("and info.phone = :phone ");
			}
			if(info.getHomePhone()!=null){
				sql.append("and info.home_phone = :home_phone ");
				countSql.append("and info.home_phone = :home_phone ");
			}
			if(info.getOfficePhone()!=null){
				sql.append("and info.office_phone = :office_phone ");
				countSql.append("and info.office_phone = :office_phone ");
			}
			if(info.getRemark()!=null){
				sql.append("and info.remark like :remark ");
				countSql.append("and info.remark like :remark ");
			}
			TypedQuery<Info> query = (TypedQuery<Info>) entityManager.createNativeQuery(sql.toString(),Info.class)
					.setFlushMode(FlushModeType.COMMIT);
			TypedQuery<BigInteger> countQuery=(TypedQuery<BigInteger>) entityManager.createNativeQuery(countSql.toString()).setFlushMode(FlushModeType.COMMIT);
			if(id!=null){
				query.setParameter("id", id);
				countQuery.setParameter("id", id);
			}
			if(info.getIdCardName()!=null){;
				query.setParameter("id_card_name", "%"+info.getIdCardName()+"%");
				countQuery.setParameter("id_card_name", "%"+info.getIdCardName()+"%");
			}
			if(info.getEnglishName()!=null){
				query.setParameter("english_name", "%"+info.getEnglishName()+"%");
				countQuery.setParameter("english_name", "%"+info.getEnglishName()+"%");
			}
			if(info.getAge()!=null){
				query.setParameter("age", info.getAge());
				countQuery.setParameter("age", info.getAge());
			}
			if(info.getSex()!=null){
				query.setParameter("sex", info.getSex());
				countQuery.setParameter("sex", info.getSex());
			}
			if(info.getBirthplace()!=null){
				query.setParameter("birthplace", "%"+info.getBirthplace()+"%");
				countQuery.setParameter("birthplace", "%"+info.getBirthplace()+"%");
			}
			if(info.getDateOfBirth()!=null){
				query.setParameter("date_of_birth", info.getDateOfBirth());
				countQuery.setParameter("date_of_birth", info.getDateOfBirth());
			}
			if(info.getNationality()!=null){
				query.setParameter("nationality", "%"+info.getNationality()+"%");
				countQuery.setParameter("nationality", "%"+info.getNationality()+"%");
			}
			if(info.getIdCard()!=null){
				query.setParameter("id_card", info.getIdCard());
				countQuery.setParameter("id_card", info.getIdCard());
			}
			if(info.getNation()!=null){
				query.setParameter("nation", "%"+info.getNation()+"%");
				countQuery.setParameter("nation", "%"+info.getNation()+"%");
			}
			if(info.getPoliticsStatus()!=null){
				query.setParameter("politics_status", "%"+info.getPoliticsStatus()+"%");
				countQuery.setParameter("politics_status", "%"+info.getPoliticsStatus()+"%");
			}
			if(info.getMaritalStatus()!=null){
				query.setParameter("marital_status", info.getMaritalStatus());
				countQuery.setParameter("marital_status", info.getMaritalStatus());
			}
			if(info.getProvince()!=null){
				query.setParameter("province", info.getProvince());
				countQuery.setParameter("province", info.getProvince());
			}
			if(info.getCity()!=null){
				query.setParameter("city", info.getCity());
				countQuery.setParameter("city", info.getCity());
			}
			if(info.getDistrict()!=null){
				query.setParameter("district", info.getDistrict());
				countQuery.setParameter("district", info.getDistrict());
			}
			if(info.getStreetAddress()!=null){
				query.setParameter("street_address", "%"+info.getStreetAddress()+"%");
				countQuery.setParameter("street_address", "%"+info.getStreetAddress()+"%");
			}
			if(info.getDomicileProvince()!=null){
				query.setParameter("domicile_province", info.getDomicileProvince());
				countQuery.setParameter("domicile_province", info.getDomicileProvince());
			}
			if(info.getDomicileCity()!=null){
				query.setParameter("domicile_city", info.getDomicileCity());
				countQuery.setParameter("domicile_city", info.getDomicileCity());
			}
			if(info.getDomicileDistrict()!=null){
				query.setParameter("domicile_district", info.getDomicileDistrict());
				countQuery.setParameter("domicile_district", info.getDomicileDistrict());
			}
			if(info.getDistrict()!=null){
				query.setParameter("domicile", "%"+info.getDistrict()+"%");
				countQuery.setParameter("domicile", "%"+info.getDistrict()+"%");
			}
			if(info.getMailbox()!=null){
				query.setParameter("mailbox", info.getMailbox());
				countQuery.setParameter("mailbox", info.getMailbox());
			}
			if(info.getPhone()!=null){
				query.setParameter("phone", info.getPhone());
				countQuery.setParameter("phone", info.getPhone());
			}
			if(info.getHomePhone()!=null){
				query.setParameter("home_phone", info.getHomePhone());
				countQuery.setParameter("home_phone", info.getHomePhone());
			}
			if(info.getOfficePhone()!=null){
				query.setParameter("office_phone", info.getOfficePhone());
				countQuery.setParameter("office_phone", info.getOfficePhone());
			}
			if(info.getRemark()!=null){
				query.setParameter("remark", "%"+info.getRemark()+"%");
				countQuery.setParameter("remark", "%"+info.getRemark()+"%");
			}
			int total = Integer.valueOf(countQuery.getResultList().get(0).toString());
			int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
			if (totalPages < pageable.getPageNumber()) {
				pageable.setPageNumber(totalPages);
			}
			query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			query.setMaxResults(pageable.getPageSize());
			return new Page<Info>(query.getResultList(), total, pageable);
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
