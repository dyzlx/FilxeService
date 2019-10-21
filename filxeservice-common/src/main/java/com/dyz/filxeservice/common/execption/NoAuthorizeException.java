package com.dyz.filxeservice.common.execption;

@SuppressWarnings("serial")
public class NoAuthorizeException extends BusinessException {

	public NoAuthorizeException() {
		super();
	}

	public NoAuthorizeException(String message) {
		super(message);
	}

	public NoAuthorizeException(int code, String message) {
		super(message);
		this.code = code;
	}

	public NoAuthorizeException(String message, Throwable cause) {
		super(message, cause);
	}
}
