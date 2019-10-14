package com.dyz.filxeservice.sal.bo;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LogicFileQueryBo {

	private String logicFileName;
	
	private String ishared;
	
	private String partitionName;
	
	private Date fromTime;
	
	private Date toTime;
}
