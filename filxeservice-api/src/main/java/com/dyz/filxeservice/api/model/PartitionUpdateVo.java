package com.dyz.filxeservice.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartitionUpdateVo {

	private Integer partitionId;

	private String partitionName;

	private String description;
}
