package com.dyz.filxeservice.common.execption;

@SuppressWarnings("serial")
public class NoAuthorizeException extends RuntimeException{

	public NoAuthorizeException()
	{
		super();
	}
	
	public NoAuthorizeException(String message)
	{
		super(message);
	}
	
	public NoAuthorizeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
