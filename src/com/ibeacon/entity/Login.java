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
@Table(name = "login")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "login_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Login extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7417576756994385989L;
	/**
	 * 手机号码(登录账号)
	 */
	private String mobilePhone;
	/**
	 * 邮箱(登录邮箱)
	 */
	private String mailBox;
	/**
	 * 昵称(网名)
	 */
	private String nickName;
	/**
	 * 登录密码
	 */
	private String passWord;
	/**
	 *是否锁定 
	 */
	private String isLocked;
	/**
	 * 类型
	 */
	private String type;
	
	/** Info */
	private Set<Info> info = new HashSet<Info>();
	
	/** OldMan */
	private Set<OldMan> oldMan = new HashSet<OldMan>();
	
	/** I beacon */
	private Set<Ibeacon> ibeacon = new HashSet<Ibeacon>();
	
	/** Acceptor */
	private Set<Acceptor> acceptor = new HashSet<Acceptor>();

	/** Document */
	private Set<Document> document = new HashSet<Document>();
	
	/** Fence */
	private Set<Fence> fence = new HashSet<Fence>();
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "login_info")
	public Set<Info> getInfo() {
		return info;
	}
	public void setInfo(Set<Info> info) {
		this.info = info;
	}
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "login_old_man")
	public Set<OldMan> getOldMan() {
		return oldMan;
	}
	public void setOldMan(Set<OldMan> oldman) {
		this.oldMan = oldman;
	}
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "login_ibeacon")
	public Set<Ibeacon> getIbeacon() {
		return ibeacon;
	}
	public void setIbeacon(Set<Ibeacon> ibeacon) {
		this.ibeacon = ibeacon;
	}
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "login_acceptor")
	public Set<Acceptor> getAcceptor() {
		return acceptor;
	}
	public void setAcceptor(Set<Acceptor> acceptor) {
		this.acceptor = acceptor;
	}
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "login_document")
	public Set<Document> getDocument() {
		return document;
	}
	public void setDocument(Set<Document> document) {
		this.document = document;
	}
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "login_fence")
	public Set<Fence> getFence() {
		return fence;
	}
	public void setFence(Set<Fence> fence) {
		this.fence = fence;
	}
}
