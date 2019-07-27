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
@Table(name = "vo_2")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "IBeaconVo_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IBeaconVo extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String UUID;
	private String major;
	private String minor;
	private String time;
	private String oldMan;
	private String name;
	private String address;
	private String alarmTime;
	private String alarmVoice;
	private String phone;
}
