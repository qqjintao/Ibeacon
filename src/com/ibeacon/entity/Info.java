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
@Table(name = "info")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "info_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Info extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3618586219813898468L;
	/**
	 * 头像
	 */
	private String headPortrait;
	/**
	 * 身份证姓名
	 */
	private String idCardName;
	/**
	 * 英文名字
	 */
	private String englishName;
	/**
	 * 年龄
	 */
	private String age;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 出生地
	 */
	private String birthplace;
	/**
	 * 出生日期
	 */
	private String dateOfBirth;
	/**
	 * 国籍
	 */
	private String nationality;
	/**
	 * 身份证
	 */
	private String idCard;
	/**
	 * 名族
	 */
	private String nation;
	/**
	 * 政治面貌
	 */
	private String politicsStatus;
	/**
	 * 婚姻状况
	 */
	private String maritalStatus;
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
	 * 现居住地街道地址
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
	 * 户口居住街道地址
	 */
	private String domicile;
	/**
	 * 邮箱
	 */
	private String mailbox;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 住宅电话
	 */
	private String homePhone;
	/**
	 * 办公电话
	 */
	private String officePhone;
	/**
	 * 备注
	 */
	private String remark;
	
	/** Login */
	private Set<Login> login = new HashSet<Login>();
	
	/** OldMan */
	private Set<OldMan> oldMan = new HashSet<OldMan>();
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "login_info")
	public Set<Login> getLogin() {
		return login;
	}
	public void setLogin(Set<Login> login) {
		this.login = login;
	}
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "info_old_man")
	public Set<OldMan> getOldMan() {
		return oldMan;
	}
	public void setOldMan(Set<OldMan> oldman) {
		this.oldMan = oldman;
	}
}
