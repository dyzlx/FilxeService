package com.dyz.filxeservice.sal.bo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LogicFileInfoBo {

	private String logicFileName;
	
	private String createTime;
	
	private String ishared;
	
	private String partitionName;
}
