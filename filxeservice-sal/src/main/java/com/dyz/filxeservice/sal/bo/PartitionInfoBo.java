package com.dyz.filxeservice.sal.bo;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PartitionInfoBo {

	private String partitionName;
	
	private String description;
	
	private Date createTime;
}
