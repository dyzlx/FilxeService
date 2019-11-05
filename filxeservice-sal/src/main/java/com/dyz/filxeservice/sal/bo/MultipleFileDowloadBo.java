package com.dyz.filxeservice.sal.bo;

import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class MultipleFileDowloadBo {

	private String compressionFileName;
	
	private List<Integer> logicFileIds;
}
