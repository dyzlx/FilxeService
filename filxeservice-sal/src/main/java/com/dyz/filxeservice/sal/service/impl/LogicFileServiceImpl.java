package com.dyz.filxeservice.sal.service.impl;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dyz.filxeservice.common.execption.IllegalParamException;
import com.dyz.filxeservice.common.execption.NoDataException;
import com.dyz.filxeservice.domain.entity.LogicFile;
import com.dyz.filxeservice.domain.entity.Partition;
import com.dyz.filxeservice.domain.entity.PhysicalFile;
import com.dyz.filxeservice.domain.repository.LogicFileRepository;
import com.dyz.filxeservice.domain.repository.PartitionRepository;
import com.dyz.filxeservice.domain.repository.PhysicalFileRepository;
import com.dyz.filxeservice.sal.bo.LogicFileInfoBo;
import com.dyz.filxeservice.sal.bo.LogicFileQueryBo;
import com.dyz.filxeservice.sal.bo.LogicFileUpdateBo;
import com.dyz.filxeservice.sal.service.LogicFileService;
import com.dyz.filxeservice.sal.translation.LogicFileModelTranslator;

@Service
public class LogicFileServiceImpl implements LogicFileService {

	@Autowired
	private LogicFileRepository logicFileRepository;

	@Autowired
	private PartitionRepository partitionRepository;

	@Autowired
	private PhysicalFileRepository physicalFileRepository;

	@Override
	public List<LogicFileInfoBo> queryLogicFileInfo(@NotNull LogicFileQueryBo queryBo) {
		if (Objects.isNull(queryBo)) {
			throw new IllegalParamException("param can not be null");
		}
		List<LogicFile> entityList = logicFileRepository.queryLogicFiles(queryBo.getLogicFileName(),
				queryBo.getPartitionId(), queryBo.getIshared(), queryBo.getUserId());
		return LogicFileModelTranslator.toBoList(entityList);
	}

	@Override
	public void deleteLogicFile(@NotNull Integer logicFileId, @NotNull Integer userId) {
		if (!ObjectUtils.allNotNull(logicFileId, userId)) {
			throw new IllegalParamException("param can not be null");
		}
		LogicFile logicFile = logicFileRepository.queryByIdAndUserId(logicFileId, userId);
		if (Objects.isNull(logicFile)) {
			throw new NoDataException("no such logic file");
		}
		int physicalFileId = logicFile.getPhysicaFileId();
		logicFileRepository.delete(logicFile);
		List<LogicFile> others = logicFileRepository.queryByPhysicaFileId(physicalFileId);
		if (CollectionUtils.isEmpty(others)) {
			PhysicalFile physicalFile = physicalFileRepository.getOne(physicalFileId);
			if (Objects.isNull(physicalFile)) {
				throw new NoDataException("no such physical file");
			}
			File file = new File(physicalFile.getLocation());
			if (file.exists() && FileUtils.deleteQuietly(file)) {
				physicalFileRepository.delete(physicalFile);
			}
		}
	}

	@Override
	public void updateLogicFileInfo(@NotNull LogicFileUpdateBo updateBo) {
		if (Objects.isNull(updateBo)) {
			throw new IllegalParamException("param can not be null");
		}
		LogicFile updatedLogicFile = logicFileRepository.queryById(updateBo.getLogicFileId());
		Partition partotion = partitionRepository.queryById(updateBo.getPartitionId());
		if (!ObjectUtils.allNotNull(updatedLogicFile, partotion)) {
			throw new NoDataException("no such logic file or partition");
		}
		updatedLogicFile.setName(updateBo.getLogicFileName());
		updatedLogicFile.setPartitionId(partotion.getId());
		updatedLogicFile.setShared(updateBo.isIshared());
		logicFileRepository.save(updatedLogicFile);
	}
}
