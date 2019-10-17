package com.dyz.filxeservice.sal.bo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LogicFileInfoBo {
	
	private int logicFileId;

	private String logicFileName;
	
	private String createTime;
	
	private String ishared;
	
	private int partitionId;
	
	private int userId;
}
