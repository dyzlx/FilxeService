package com.dyz.filxeservice.sal.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PartitionUpdateBo {
	
	private Integer partitionId;

	private String partitionName;
	
	private String description;
}
