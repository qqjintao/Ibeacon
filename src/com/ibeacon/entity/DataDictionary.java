package com.ibeacon.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "data_dictionary")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "data_dictionary_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataDictionary extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3563865081114059523L;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String details;
	/**
	 * 父节点id
	 */
	private Long parentId;
}
