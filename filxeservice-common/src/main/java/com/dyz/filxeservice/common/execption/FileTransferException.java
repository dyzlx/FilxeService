package com.dyz.filxeservice.common.execption;

@SuppressWarnings("serial")
public class FileTransferException extends BusinessException {

	public FileTransferException() {
		super();
	}

	public FileTransferException(String message) {
		super(message);
	}

	public FileTransferException(int code, String message) {
		super(message);
		this.code = code;
	}

	public FileTransferException(String message, Throwable cause) {
		super(message, cause);
	}
}
