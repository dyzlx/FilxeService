package com.dyz.filxeservice.sal.bo;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PartitionQueryBo {
	
	private String partitionName;
	
	private int userId;
	
	private Date fromDate;
	
	private Date toDate;
}
