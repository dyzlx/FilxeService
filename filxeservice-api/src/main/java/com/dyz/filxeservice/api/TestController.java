package com.dyz.filxeservice.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dyz.filxeservice.api.model.User;

@RestController
@RequestMapping(value = "test")
public class TestController {

	@RequestMapping(value="string"
			,method=RequestMethod.GET
			,produces={"application/json","application/xml"})
	public ResponseEntity<String> test()
	{
		String s = "hello filxeservice !";
		return ResponseEntity.status(HttpStatus.OK).body(s);
	}
	
	@RequestMapping(value="user"
			,method=RequestMethod.GET
			,produces={"application/json","application/xml"})
	public ResponseEntity<User> test2()
	{
		User user = User.builder().name("dyz").password("123456").build();
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
}
