package com.dyz.filxeservice.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogicFileInfoVo {
	
	private int logicFileId;

	private String logicFileName;
	
	private String createTime;
	
	private boolean ishared;
	
	private int partitionId;
	
	private int userId;
}
