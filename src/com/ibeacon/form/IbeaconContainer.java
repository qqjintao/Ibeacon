package com.ibeacon.form;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ibeacon.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "vo_1")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "IbeaconContainer_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IbeaconContainer extends BaseEntity{
	/**
	 * 
	 */
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
	
	/** 老人表ID */
	private Long oldman;

	/** 老人表姓名 */
	private String name;
}
