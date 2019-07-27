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
@Table(name = "ibeacon")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ibeacon_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ibeacon extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	/**
	 * UUID
	 */
	private String UUID;
	/**
	 * Major
	 */
	private String major;
	/**
	 * Minor
	 */
	private String minor;
	/**
	 * 当前模式
	 */
	private String mode;
	/**
	 * 当前电量
	 */
	private String electric;
	/**
	 * 备注
	 */
	private String remark;
	
	/** Login */
	private Set<Login> login = new HashSet<Login>();
	
	/** OldMan */
	private Set<OldMan> oldMan = new HashSet<OldMan>();
	
	/** Acceptor */
	private Set<Acceptor> acceptor = new HashSet<Acceptor>();
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "login_ibeacon")
	public Set<Login> getLogin() {
		return login;
	}
	public void setLogin(Set<Login> login) {
		this.login = login;
	}
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "ibeacon_old_man")
	public Set<OldMan> getOldMan() {
		return oldMan;
	}
	public void setOldMan(Set<OldMan> oldMan) {
		this.oldMan = oldMan;
	}	
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "ibeacon_acceptor")
	public Set<Acceptor> getAcceptor() {
		return acceptor;
	}
	public void setAcceptor(Set<Acceptor> acceptor) {
		this.acceptor = acceptor;
	}
}
