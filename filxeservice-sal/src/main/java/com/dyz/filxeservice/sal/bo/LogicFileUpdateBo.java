package com.dyz.filxeservice.sal.bo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LogicFileUpdateBo {

	private String logicFileName;
	
	private String partitionName;
	
	private String ishared;
}
