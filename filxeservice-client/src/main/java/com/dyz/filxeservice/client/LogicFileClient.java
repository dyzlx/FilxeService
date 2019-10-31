package com.dyz.filxeservice.client;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface LogicFileClient {

	void deleteLogicFile(Integer logicFileId, Integer userId);
	
	void deleteLogicFiles(List<Integer> logicFileIds, Integer userId);
	
	List<Integer> uploadFiles(MultipartFile[] files, boolean ishared, Integer userId);
}
