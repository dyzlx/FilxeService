package com.dyz.filxeservice.sal.bo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LogicFileUploadBo {
	
	private boolean ishared;
	
	private Integer partitionId;

}
