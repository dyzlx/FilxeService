package com.dyz.filxeservice.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {

	@Builder.Default
	private int code = 1;
	
	@Builder.Default
	private String message = "Success";
	
	private Object content;
}
