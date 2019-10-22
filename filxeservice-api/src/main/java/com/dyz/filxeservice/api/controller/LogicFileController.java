package com.dyz.filxeservice.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dyz.filxeservice.api.model.LogicFileInfoVo;
import com.dyz.filxeservice.api.model.LogicFileUpdateVo;
import com.dyz.filxeservice.api.model.Result;
import com.dyz.filxeservice.api.translation.LogicFileModelTranslator;
import com.dyz.filxeservice.sal.bo.LogicFileQueryBo;
import com.dyz.filxeservice.sal.service.LogicFileService;

@RestController
@RequestMapping(value = "logicfile")
public class LogicFileController {

	@Autowired
	private LogicFileService logicfileService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	public ResponseEntity<Result> queryLogicFile(
			@RequestParam(required = false) String logicFileName,
			@RequestParam(required = false) String ishared, 
			@RequestParam(required = false) Integer partitionId,
			@RequestParam(required = false) Integer userId, 
			@RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate) {
		LogicFileQueryBo queryBo = LogicFileModelTranslator.toBo(
				logicFileName, ishared, partitionId, userId, fromDate,toDate);
		List<LogicFileInfoVo> result = LogicFileModelTranslator.toVoList(logicfileService.queryLogicFileInfo(queryBo));
		return ResponseEntity.status(HttpStatus.OK).body(Result.builder().content(result).build());
	}

	@RequestMapping(value = "{logicFileId}", method = RequestMethod.DELETE, produces = { "application/json",
			"application/xml" })
	public ResponseEntity<Result> deleteLogicFile(@PathVariable Integer logicFileId, @RequestHeader Integer userId) {
		logicfileService.deleteLogicFile(logicFileId, userId);
		return ResponseEntity.status(HttpStatus.OK).body(Result.builder().build());
	}

	@RequestMapping(value = "", method = RequestMethod.PUT, produces = { "application/json", "application/xml" })
	public ResponseEntity<Result> updateLogicFileInfo(@RequestBody LogicFileUpdateVo updateVo,
			@RequestHeader Integer userId) {
		logicfileService.updateLogicFileInfo(LogicFileModelTranslator.toBo(updateVo), userId);
		return ResponseEntity.status(HttpStatus.OK).body(Result.builder().build());
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST,
			produces = { "application/json","application/xml" },
			consumes = { "multipart/form-data" })
	public ResponseEntity<Result> uploadLogicFile(@RequestParam MultipartFile file,
			@RequestParam(required = false) Integer partitionId, @RequestParam(required = false) boolean ishared,
			@RequestHeader Integer userId) {
		logicfileService.uploadFile(file, LogicFileModelTranslator.toBo(partitionId, ishared), userId);
		return ResponseEntity.status(HttpStatus.OK).body(Result.builder().build());
	}

	@RequestMapping(value = "download/{logicFileId}", method = RequestMethod.GET)
	public ResponseEntity<?> downloadLogicFile(
			@PathVariable Integer logicFileId,
			@RequestHeader Integer userId,
			HttpServletResponse response) {
		logicfileService.downloadFile(logicFileId, userId, response);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
