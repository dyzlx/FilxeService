package com.dyz.filxeservice.api.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dyz.filxeservice.api.model.Result;
import com.dyz.filxeservice.common.execption.BusinessException;

@ControllerAdvice
public class GlobalExceptionInterceptor {

	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<Result> handlerBusinessException(BusinessException exception) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(Result.builder().code(exception.getCode()).message(exception.getMessage()).build());
	}

}
