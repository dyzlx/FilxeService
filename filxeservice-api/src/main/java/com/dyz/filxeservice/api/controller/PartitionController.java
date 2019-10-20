package com.dyz.filxeservice.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "partition")
public class PartitionController {

	@RequestMapping(value=""
			,method=RequestMethod.GET
			,produces={"application/json","application/xml"})
	public ResponseEntity<String> queryPartition()
	{
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
