package com.dyz.filxeservice.client.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.dyz.filxeservice.client.LogicFileClient;

@Component
public class LogicFileClientImpl implements LogicFileClient {

	@Override
	public void deleteLogicFile(Integer logicFileId, Integer userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteLogicFiles(List<Integer> logicFileIds, Integer userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer> uploadFiles(MultipartFile[] files, boolean ishared, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
