package com.dyz.filxeservice.sal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dyz.filxeservice.common.execption.IllegalOperationException;
import com.dyz.filxeservice.common.execption.IllegalParamException;
import com.dyz.filxeservice.common.execption.NoDataException;
import com.dyz.filxeservice.domain.entity.LogicFile;
import com.dyz.filxeservice.domain.entity.Partition;
import com.dyz.filxeservice.domain.repository.LogicFileRepository;
import com.dyz.filxeservice.domain.repository.PartitionRepository;
import com.dyz.filxeservice.sal.bo.PartitionCreateBO;
import com.dyz.filxeservice.sal.bo.PartitionInfoBo;
import com.dyz.filxeservice.sal.bo.PartitionQueryBo;
import com.dyz.filxeservice.sal.bo.PartitionUpdateBo;
import com.dyz.filxeservice.sal.service.PartitionService;
import com.dyz.filxeservice.sal.translation.PartitionModelTranslator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PartitionServiceImpl implements PartitionService {

	@Autowired
	private PartitionRepository partitionRepository;

	@Autowired
	private LogicFileRepository logicFileRepository;

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public List<PartitionInfoBo> queryPartitionInfo(@NotNull PartitionQueryBo queryBo) {
		log.info("begin to query partition, queryBo = {}", queryBo);
		if (Objects.isNull(queryBo)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		List<Partition> entityList = partitionRepository.queryPartitions(queryBo.getPartitionName(),
				queryBo.getUserId(), queryBo.getFromDate(), queryBo.getToDate());
		log.info("end of query partition, result = {}", entityList);
		return PartitionModelTranslator.toBoList(entityList);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createPartition(@NotNull PartitionCreateBO createBo, @NotNull Integer userId) {
		log.info("begin to create partition, partitionBo = {}, userId = {}", createBo, userId);
		String partitionName = createBo.getPartitionName();
		if (!ObjectUtils.allNotNull(createBo, partitionName, userId)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		Partition newPartition = Partition.builder().createTime(new Date()).description(createBo.getDescription())
				.name(partitionName).userId(userId).isDefault(false).build();
		partitionRepository.save(newPartition);
		log.info("end of create partition, partition = {}", newPartition);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updatePartition(@NotNull PartitionUpdateBo updateBo, @NotNull Integer userId) {
		log.info("begin to update partition info, updateBo = {}, userId = {}", updateBo, userId);
		if (!ObjectUtils.allNotNull(updateBo, userId)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		Partition partition = partitionRepository.queryByIdAndUserId(updateBo.getPartitionId(), userId);
		if (Objects.isNull(partition)) {
			log.warn("no such partition");
			throw new NoDataException(0, "no such partition");
		}
		if (partition.isDefault()) {
			log.warn("default partition can not be updated");
			throw new IllegalOperationException(0, "default partition can not be updated");
		}
		partition.setName(updateBo.getPartitionName());
		partition.setDescription(updateBo.getDescription());
		partitionRepository.save(partition);
		log.info("end of update partition info, partition = {}", partition);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void deletePartition(@NotNull Integer partitionId, @NotNull Integer userId) {
		log.info("begin to delete partiton, partitionId = {}, userId = {}", partitionId, userId);
		if (!ObjectUtils.allNotNull(partitionId, userId)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		Partition partition = partitionRepository.queryByIdAndUserId(partitionId, userId);
		if (Objects.isNull(partition)) {
			log.warn("no such partition");
			throw new NoDataException(0, "no such partition");
		}
		if (partition.isDefault()) {
			log.warn("default partition can not be deleted");
			throw new IllegalOperationException(0, "default partition can not be deleted");
		}
		List<LogicFile> logicFilesInPartition = logicFileRepository.queryByPartitionId(partition.getId());
		if (!CollectionUtils.isEmpty(logicFilesInPartition)) {
			log.warn("can not delete partition which contains file");
			throw new IllegalOperationException(0, "can not delete partition which contains file");
		}
		partitionRepository.delete(partition);
		log.info("end of delete parition, deleted partition = {}", partition);
	}
}
