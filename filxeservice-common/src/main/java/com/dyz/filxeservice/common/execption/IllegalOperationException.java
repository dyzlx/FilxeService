package com.dyz.filxeservice.common.execption;

@SuppressWarnings("serial")
public class IllegalOperationException extends BusinessException {

	public IllegalOperationException() {
		super();
	}

	public IllegalOperationException(String message) {
		super(message);
	}

	public IllegalOperationException(int code, String message) {
		super(message);
		this.code = code;
	}

	public IllegalOperationException(String message, Throwable cause) {
		super(message, cause);
	}
}
