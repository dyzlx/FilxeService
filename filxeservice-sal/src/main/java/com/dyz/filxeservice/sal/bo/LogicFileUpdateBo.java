package com.dyz.filxeservice.sal.bo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LogicFileUpdateBo {
	
	private Integer logicFileId;

	private String logicFileName;
	
	private Integer partitionId;
	
	private boolean ishared;
}
