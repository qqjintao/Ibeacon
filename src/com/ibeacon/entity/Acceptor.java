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
@Table(name = "acceptor")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "acceptor_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Acceptor extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 接收器名称
	 */
	private String name;
	/**
	 * 位置
	 */
	private String address;
	/**
	 * 报警时间
	 */
	private String alarmTime;
	/**
	 * 报警声音
	 */
	private String alarmVoice;
	/**
	 * 是否发送短信
	 */
	private String isSend;
	/**
	 * 发送手机
	 */
	private String phone;
	/**
	 * 备注
	 */
	private String remark;
	
	/** Login */
	private Set<Login> login = new HashSet<Login>();
	
	/** I beacon */
	private Set<Ibeacon> ibeacon = new HashSet<Ibeacon>();

	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "login_acceptor")
	public Set<Login> getLogin() {
		return login;
	}
	public void setLogin(Set<Login> login) {
		this.login = login;
	}
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "ibeacon_acceptor")
	public Set<Ibeacon> getIbeacon() {
		return ibeacon;
	}
	public void setIbeacon(Set<Ibeacon> ibeacon) {
		this.ibeacon = ibeacon;
	}
}
