package com.dyz.filxeservice.api.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PartitionInfoVo {

	private int partitionId;

	private String partitionName;
	
	private String description;
	
	private String createTime;
	
	private int userId;
	
	private boolean isDefault;
}
