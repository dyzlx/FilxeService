package com.dyz.filxeservice.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User
{
	private String name;
	
	private String password;
}