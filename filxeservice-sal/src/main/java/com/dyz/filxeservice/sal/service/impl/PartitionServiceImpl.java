package com.dyz.filxeservice.sal.service.impl;

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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.dyz.filxeservice.common.model.UserContextHolder.getUserContext;
import static com.dyz.filxeservice.common.model.UserContextHolder.getUserId;

@Slf4j
@Service
public class PartitionServiceImpl implements PartitionService {

    @Autowired
    private PartitionRepository partitionRepository;

    @Autowired
    private LogicFileRepository logicFileRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public List<PartitionInfoBo> queryPartitionInfo(PartitionQueryBo queryBo) {
        log.info("begin to query partition, queryBo = {}, user context = {}", queryBo, getUserContext());
        if (Objects.isNull(queryBo)) {
            throw new IllegalParamException(0, "param can not be null");
        }
        List<Partition> entityList = partitionRepository.queryPartitions(queryBo.getPartitionId(), queryBo.getPartitionName(),
                queryBo.getUserId(), queryBo.getFromDate(), queryBo.getToDate());
        log.info("end of query partition, result = {}", entityList);
        return PartitionModelTranslator.toBoList(entityList);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public Integer createPartition(PartitionCreateBO createBo) {
        log.info("begin to create partition, partitionBo = {}, user context = {}", createBo, getUserContext());
        String partitionName = createBo.getPartitionName();
        if (!ObjectUtils.allNotNull(createBo, partitionName)) {
            throw new IllegalParamException(0, "param can not be null");
        }
        Partition newPartition = Partition.builder().createTime(new Date()).description(createBo.getDescription())
                .name(partitionName).userId(getUserId()).isDefault(false).build();
        partitionRepository.save(newPartition);
        log.info("end of create partition, partition = {}", newPartition);
        return newPartition.getId();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void updatePartition(PartitionUpdateBo updateBo) {
        log.info("begin to update partition info, updateBo = {}, user context = {}", updateBo, getUserContext());
        if (Objects.isNull(updateBo)) {
            throw new IllegalParamException(0, "param can not be null");
        }
        Partition partition = partitionRepository.queryByIdAndUserId(updateBo.getPartitionId(), getUserId());
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
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void deletePartition(Integer partitionId) {
        log.info("begin to delete partiton, partitionId = {}, user context = {}", partitionId, getUserContext());
        if (Objects.isNull(partitionId)) {
            throw new IllegalParamException(0, "param can not be null");
        }
        Partition partition = partitionRepository.queryByIdAndUserId(partitionId, getUserId());
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
