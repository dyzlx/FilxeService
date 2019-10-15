package com.dyz.filxeservice.common.execption;

@SuppressWarnings("serial")
public class IllegalParamException extends RuntimeException {

	public IllegalParamException()
	{
		super();
	}
	
	public IllegalParamException(String message)
	{
		super(message);
	}
	
	public IllegalParamException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
