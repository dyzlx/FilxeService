package com.dyz.filxeservice.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dyz.filxeservice.api.model.PartitionCreateVo;
import com.dyz.filxeservice.api.model.PartitionInfoVo;
import com.dyz.filxeservice.api.model.PartitionUpdateVo;
import com.dyz.filxeservice.api.model.Result;
import com.dyz.filxeservice.api.translation.PartitionModelTranslator;
import com.dyz.filxeservice.sal.bo.PartitionQueryBo;
import com.dyz.filxeservice.sal.service.PartitionService;

@RestController
@RequestMapping(value = "partitions")
public class PartitionController {

	@Autowired
	private PartitionService partitionService;

	@RequestMapping(value = "", method = RequestMethod.GET,
			produces = { "application/json", "application/xml" })
	public ResponseEntity<Result> queryPartition(
            @RequestParam(required = false) Integer partitionId,
			@RequestParam(required = false) String partitionName,
			@RequestParam(required = false) Integer userId,
			@RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate) {
		PartitionQueryBo queryBo = PartitionModelTranslator.toBo(partitionId, partitionName, userId, fromDate, toDate);
		List<PartitionInfoVo> resultList = PartitionModelTranslator
				.toVoList(partitionService.queryPartitionInfo(queryBo));
		return ResponseEntity.status(HttpStatus.OK).body(Result.builder().content(resultList).build());
	}

	@RequestMapping(value = "", method = RequestMethod.POST,
			produces = { "application/json","application/xml" },
			consumes = { "application/json", "application/xml" })
	public ResponseEntity<Result> createPartition(@Validated @RequestBody PartitionCreateVo createVo) {
		Integer id = partitionService.createPartition(PartitionModelTranslator.toBo(createVo));
		return ResponseEntity.status(HttpStatus.OK)
		        .body(Result.builder().content(id).build());
	}

	@RequestMapping(value = "", method = RequestMethod.PUT,
			produces = { "application/json","application/xml" },
			consumes = { "application/json", "application/xml" })
	public ResponseEntity<Result> updatePartition(@Validated @RequestBody PartitionUpdateVo updateVo) {
		partitionService.updatePartition(PartitionModelTranslator.toBo(updateVo));
		return ResponseEntity.status(HttpStatus.OK).body(Result.builder().build());
	}
	
	@RequestMapping(value = "{partitionId}", method = RequestMethod.DELETE,
			produces = { "application/json","application/xml" })
	public ResponseEntity<Result> deletePartition(@PathVariable Integer partitionId) {
		partitionService.deletePartition(partitionId);
		return ResponseEntity.status(HttpStatus.OK).body(Result.builder().build());
	}
}
