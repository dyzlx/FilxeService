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
@Table(name = "fpartition")
public class Partition {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	@Column(name = "description", length = 400)
	private String description;
	
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	@Column(name = "user_id", nullable = false)
	private int userId;
}
