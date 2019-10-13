package com.dyz.filxeservice.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"name"})
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "physicalfile")
public class PhysicalFile {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	@Column(name = "md5", length = 100)
	private String md5;
	
	@Column(name = "type", length = 120, nullable = false)
	private String type;
	
	@Column(name = "location", length = 100, nullable = false)
	private String location;
	
	@Column(name = "size", nullable = false)
	private int size;
	
	@Column(name = "upload_time", nullable = false)
	private Date uploadTime;
}
