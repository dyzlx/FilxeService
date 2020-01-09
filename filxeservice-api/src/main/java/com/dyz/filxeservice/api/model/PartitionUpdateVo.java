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
public class PartitionUpdateVo {

	@NotNull
	private Integer partitionId;

	@Size(min = 1, max = 50)
	@NotBlank
	private String partitionName;

	@Size(max = 500)
	@NotBlank
	private String description;
}
