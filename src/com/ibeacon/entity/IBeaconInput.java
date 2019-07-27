package com.ibeacon.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ibeacon_input")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ibeacon_input_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IBeaconInput extends BaseEntity{
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
	 * 设备名称
	 */
	private String name;
	/**
	 * 时间
	 */
	private String time;
	
}
