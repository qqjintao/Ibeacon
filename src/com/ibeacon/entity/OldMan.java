package com.ibeacon.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "old_man")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "old_man_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OldMan extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5530672329801106796L;
	/**
	 * 头像
	 */
	private String headPortrait;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 年龄
	 */
	private String age;
	/**
	 * 曾用名
	 */
	private String englishName;
	/**
	 * 国籍
	 */
	private String nationality;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 政治面貌
	 */
	private String politicsStatus;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 现居住地省
	 */
	private String province;
	/**
	 * 现居住地市
	 */
	private String city;
	/**
	 * 现居住地区
	 */
	private String district;
	/**
	 * 现居住地具体街道
	 */
	private String streetAddress;
	/**
	 * 户口所在省	
	 */
	private String domicileProvince;
	/**
	 * 户口所在市
	 */
	private String domicileCity;
	/**
	 * 户口所在区
	 */
	private String domicileDistrict;
	/**
	 * 户口所在具体街道
	 */
	private String domicile;
	/**
	 * 身份证
	 */
	private String identityCard;
	/**
	 * 身体状况
	 */
	private String physical;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 关系
	 */
	private String relation;
	/**
	 * 医保账户
	 */
	private String account;
	/**
	 * 主负责医生
	 */
	private String doctor;
	/**
	 * 医生电话
	 */
	private String doctorPhone;
	/**
	 * 主负责护士
	 */
	private String nurse;
	/**
	 * 护士电话
	 */
	private String nursePhone;
	/**
	 * 紧急联系人
	 */
	private String emergencyName;
	/**
	 * 紧急联系人电话
	 */
	private String emergencyCall;
	/**
	 * 备注
	 */
	private String remark;
	
	/** Login */
	private Set<Login> login = new HashSet<Login>();
	
	/** I beacon */
	private Set<Ibeacon> ibeacon = new HashSet<Ibeacon>();
	
	/** Info */
	private Set<Info> info = new HashSet<Info>();
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "login_old_man")
	public Set<Login> getLogin() {
		return login;
	}
	public void setLogin(Set<Login> login) {
		this.login = login;
	}
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "ibeacon_old_man")
	public Set<Ibeacon> getIbeacon() {
		return ibeacon;
	}
	public void setIbeacon(Set<Ibeacon> ibeacon) {
		this.ibeacon = ibeacon;
	}
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "info_old_man")
	public Set<Info> getInfo() {
		return info;
	}
	public void setInfo(Set<Info> info) {
		this.info = info;
	}
}
