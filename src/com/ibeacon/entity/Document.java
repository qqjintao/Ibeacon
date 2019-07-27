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
@Table(name = "document")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "document_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文档名称
	 */
	private String name;
	/**
	 * 文档地址
	 */
	private String loadAddress;
	/**
	 * 备注
	 */
	private String remark;
	
	/** Login */
	private Set<Login> login = new HashSet<Login>();
	
	@NotEmpty
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "login_document")
	public Set<Login> getLogin() {
		return login;
	}
	public void setLogin(Set<Login> login) {
		this.login = login;
	}

}
