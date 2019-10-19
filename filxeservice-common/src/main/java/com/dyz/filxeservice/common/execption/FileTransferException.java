package com.dyz.filxeservice.common.execption;

@SuppressWarnings("serial")
public class FileTransferException extends RuntimeException {

	public FileTransferException()
	{
		super();
	}
	
	public FileTransferException(String message)
	{
		super(message);
	}
	
	public FileTransferException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
