package com.dyz.filxeservice.common.execption;


@SuppressWarnings("serial")
public class IllegalOperationException extends RuntimeException {

	public IllegalOperationException()
	{
		super();
	}
	
	public IllegalOperationException(String message)
	{
		super(message);
	}
	
	public IllegalOperationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
