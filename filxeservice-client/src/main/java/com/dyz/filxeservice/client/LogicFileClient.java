package com.dyz.filxeservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.dyz.filxeservice.client.config.ClientErrorConfiguration;
import com.dyz.filxeservice.client.config.MultipartSupportConfiguration;
import com.dyz.filxeservice.client.model.Result;

@FeignClient(value = "filxeservice", configuration = {MultipartSupportConfiguration.class, ClientErrorConfiguration.class})
public interface LogicFileClient {

	/**
	 * delete file by id and user id
	 * @param logicFileId
	 * @param userId
	 */
	@RequestMapping(value = "/filxeservice/logicfiles/{logicFileId}",
			method = RequestMethod.DELETE,
			consumes = {"application/json", "application/xml" })
	void deleteLogicFile(@PathVariable(name = "logicFileId") Integer logicFileId,
			@RequestHeader(name = "userId") Integer userId);

	/**
	 * delete files by ids and user id
	 * @param logicFileIds
	 * @param userId
	 */
	@RequestMapping(value = "/filxeservice/logicfiles",
			method = RequestMethod.DELETE,
			consumes = { "application/json","application/xml" })
	void deleteLogicFiles(@RequestBody List<Integer> logicFileIds,
			@RequestHeader(name = "userId") Integer userId);

	/**
	 * upload files
	 * @param file
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/filxeservice/logicfiles/upload",
			method = RequestMethod.POST,
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	Result<List<Integer>> uploadFiles(@RequestPart(name = "file") MultipartFile[] file,
			@RequestHeader(name = "userId") Integer userId);
}
