package com.dyz.filxeservice.sal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dyz.filxeservice.common.execption.IllegalOperationException;
import com.dyz.filxeservice.common.execption.IllegalParamException;
import com.dyz.filxeservice.common.execption.NoDataException;
import com.dyz.filxeservice.domain.entity.LogicFile;
import com.dyz.filxeservice.domain.entity.Partition;
import com.dyz.filxeservice.domain.repository.LogicFileRepository;
import com.dyz.filxeservice.domain.repository.PartitionRepository;
import com.dyz.filxeservice.sal.bo.PartitionInfoBo;
import com.dyz.filxeservice.sal.bo.PartitionQueryBo;
import com.dyz.filxeservice.sal.bo.PartitionUpdateBo;
import com.dyz.filxeservice.sal.service.PartitionService;
import com.dyz.filxeservice.sal.translation.PartitionModelTranslator;

@Service
public class PartitionServiceImpl implements PartitionService {

	@Autowired
	private PartitionRepository partitionRepository;

	@Autowired
	private LogicFileRepository logicFileRepository;

	@Override
	public List<PartitionInfoBo> queryPartitionInfo(@NotNull PartitionQueryBo queryBo) {
		if (Objects.isNull(queryBo)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		List<Partition> entityList = partitionRepository.queryPartitions(queryBo.getPartitionName(),
				queryBo.getUserId(), queryBo.getFromDate(), queryBo.getToDate());
		return PartitionModelTranslator.toBoList(entityList);
	}

	@Override
	public void createPartition(@NotNull String partitionName, String description, @NotNull Integer userId) {
		if (!ObjectUtils.allNotNull(partitionName, userId)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		Partition newPartition = Partition.builder().createTime(new Date()).description(description).name(partitionName)
				.userId(userId).build();
		partitionRepository.save(newPartition);
	}

	@Override
	public void updatePartition(@NotNull PartitionUpdateBo updateBo, @NotNull Integer userId) {
		if (!ObjectUtils.allNotNull(updateBo, userId)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		Partition partition = partitionRepository.queryByIdAndUserId(updateBo.getPartitionId(), userId);
		if (Objects.isNull(partition)) {
			throw new NoDataException(0, "no such partition");
		}
		partition.setName(updateBo.getPartitionName());
		partition.setDescription(updateBo.getDescription());
		partitionRepository.save(partition);
	}

	@Override
	public void deletePartition(@NotNull Integer partitionId, @NotNull Integer userId) {
		if (!ObjectUtils.allNotNull(partitionId, userId)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		Partition partition = partitionRepository.queryByIdAndUserId(partitionId, userId);
		if (Objects.isNull(partition)) {
			throw new NoDataException(0, "no such partition");
		}
		List<LogicFile> logicFilesInPartition = logicFileRepository.queryByPartitionId(partition.getId());
		if (!CollectionUtils.isEmpty(logicFilesInPartition)) {
			throw new IllegalOperationException(0, "can not delete partition which contains file");
		}
		partitionRepository.delete(partition);
	}
}
