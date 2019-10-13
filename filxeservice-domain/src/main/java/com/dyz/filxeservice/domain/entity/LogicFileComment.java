package com.dyz.filxeservice.domain.entity;

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
@EqualsAndHashCode(of = {"logicFileId", "commentId"})
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "logicfilecomment")
public class LogicFileComment {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "logic_file_id", nullable = false)
	private int logicFileId;
	
	@Column(name = "comment_id", nullable = false)
	private int commentId;
}
