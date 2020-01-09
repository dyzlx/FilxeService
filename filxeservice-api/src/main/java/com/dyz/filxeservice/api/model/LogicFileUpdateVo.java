package com.dyz.filxeservice.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogicFileUpdateVo {

	@NotNull
	private Integer logicFileId;

	@Size(min = 1, max = 100)
	@NotBlank
	private String logicFileName;

	@NotNull
	private Integer partitionId;

	@NotNull
	private boolean ishared;

}
