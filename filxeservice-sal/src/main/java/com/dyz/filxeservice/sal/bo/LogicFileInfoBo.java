package com.dyz.filxeservice.sal.bo;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LogicFileInfoBo {
	
	private int logicFileId;

	private String logicFileName;
	
	private Date createTime;
	
	private boolean ishared;
	
	private int partitionId;
	
	private int userId;
}
