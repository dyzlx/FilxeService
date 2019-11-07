package com.dyz.filxeservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


@FeignClient("filxeservice")
public interface LogicFileClient {

	@RequestMapping(value = "/filxeservice/logicfiles/{logicFileId}", method = RequestMethod.DELETE,
			consumes = { "application/json","application/xml" })
	void deleteLogicFile(@PathVariable(name = "logicFileId") Integer logicFileId,
			@RequestHeader(name = "userId") Integer userId);
	
	@RequestMapping(value = "/filxeservice/logicfiles", method = RequestMethod.DELETE,
			consumes = { "application/json","application/xml" })
	void deleteLogicFiles(@RequestBody List<Integer> logicFileIds,
			@RequestHeader(name = "userId") Integer userId);
	
	@RequestMapping(value = "/filxeservice/logicfiles/upload", method = RequestMethod.POST,
			consumes = { "multipart/form-data" })
	List<Integer> uploadFiles(@RequestPart(name = "file") MultipartFile[] file,
			@RequestHeader(name = "userId") Integer userId);
	
//	@Configuration
//    class MultipartSupportConfig {
//        @Bean
//        public Encoder feignFormEncoder() {
//            return new SpringFormEncoder();
//        }
//    }
}
