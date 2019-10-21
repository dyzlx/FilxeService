package com.dyz.filxeservice.sal.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.dyz.filxeservice.common.execption.FileTransferException;
import com.dyz.filxeservice.common.execption.IllegalParamException;
import com.dyz.filxeservice.common.execption.NoDataException;
import com.dyz.filxeservice.common.util.FileHandler;
import com.dyz.filxeservice.domain.entity.LogicFile;
import com.dyz.filxeservice.domain.entity.Partition;
import com.dyz.filxeservice.domain.entity.PhysicalFile;
import com.dyz.filxeservice.domain.repository.LogicFileRepository;
import com.dyz.filxeservice.domain.repository.PartitionRepository;
import com.dyz.filxeservice.domain.repository.PhysicalFileRepository;
import com.dyz.filxeservice.sal.bo.LogicFileInfoBo;
import com.dyz.filxeservice.sal.bo.LogicFileQueryBo;
import com.dyz.filxeservice.sal.bo.LogicFileUpdateBo;
import com.dyz.filxeservice.sal.bo.LogicFileUploadBo;
import com.dyz.filxeservice.sal.service.LogicFileService;
import com.dyz.filxeservice.sal.translation.LogicFileModelTranslator;

@Service
public class LogicFileServiceImpl implements LogicFileService {

	private static final String LOCAL_STORE_PATH = "/Volumes/DYZ-document/Program/Project";

	@Autowired
	private LogicFileRepository logicFileRepository;

	@Autowired
	private PartitionRepository partitionRepository;

	@Autowired
	private PhysicalFileRepository physicalFileRepository;

	@Override
	public List<LogicFileInfoBo> queryLogicFileInfo(@NotNull LogicFileQueryBo queryBo) {
		if (Objects.isNull(queryBo)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		List<LogicFile> entityList = logicFileRepository.queryLogicFiles(queryBo.getLogicFileName(),
				queryBo.getPartitionId(), queryBo.getIshared(), queryBo.getUserId(), queryBo.getFromTime(),
				queryBo.getToTime());
		return LogicFileModelTranslator.toBoList(entityList);
	}

	@Override
	public void deleteLogicFile(@NotNull Integer logicFileId, @NotNull Integer userId) {
		if (!ObjectUtils.allNotNull(logicFileId, userId)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		LogicFile logicFile = logicFileRepository.queryByIdAndUserId(logicFileId, userId);
		if (Objects.isNull(logicFile)) {
			throw new NoDataException(0, "no such logic file");
		}
		int physicalFileId = logicFile.getPhysicaFileId();
		logicFileRepository.delete(logicFile);
		List<LogicFile> others = logicFileRepository.queryByPhysicaFileId(physicalFileId);
		if (CollectionUtils.isEmpty(others)) {
			PhysicalFile physicalFile = physicalFileRepository.queryById(physicalFileId);
			if (Objects.isNull(physicalFile)) {
				throw new NoDataException(0, "no such physical file");
			}
			File file = new File(physicalFile.getLocation());
			if (file.exists() && FileUtils.deleteQuietly(file)) {
				physicalFileRepository.delete(physicalFile);
			}
		}
	}

	@Override
	public void updateLogicFileInfo(@NotNull LogicFileUpdateBo updateBo, @NotNull Integer userId) {
		if (Objects.isNull(updateBo)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		LogicFile updatedLogicFile = logicFileRepository.queryByIdAndUserId(updateBo.getLogicFileId(), userId);
		Partition partotion = partitionRepository.queryByIdAndUserId(updateBo.getPartitionId(), userId);
		if (!ObjectUtils.allNotNull(updatedLogicFile, partotion)) {
			throw new NoDataException(0, "no such logic file or partition");
		}
		updatedLogicFile.setName(updateBo.getLogicFileName());
		updatedLogicFile.setPartitionId(partotion.getId());
		updatedLogicFile.setShared(updateBo.isIshared());
		logicFileRepository.save(updatedLogicFile);
	}

	@Override
	public void uploadFile(@NotNull MultipartFile file, @NotNull LogicFileUploadBo uploadBo, @NotNull Integer userId) {
		if (!ObjectUtils.allNotNull(file, uploadBo, userId)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		// TO-DO:same file not store this
		String localFileName = UUID.randomUUID().toString() + file.getName();
		File localFile = FileHandler.transferToLocalFile(file, LOCAL_STORE_PATH, localFileName);
		String[] localFileNameStrs = localFileName.split(".");
		String localFileType = localFileNameStrs[localFileNameStrs.length - 1];
		PhysicalFile physicalFile = PhysicalFile.builder().location(LOCAL_STORE_PATH).name(localFileName)
				.size(localFile.length()).type(localFileType).uploadTime(new Date()).build();
		physicalFileRepository.save(physicalFile);
		int physicalFileId = physicalFile.getId();
		LogicFile newLogicFile = LogicFile.builder().createTime(new Date()).isShared(uploadBo.isIshared())
				.name(file.getName()).partitionId(uploadBo.getPartitionId()).physicaFileId(physicalFileId)
				.userId(userId).build();
		logicFileRepository.save(newLogicFile);
	}

	@Override
	public void downloadFile(@NotNull Integer logicFileId, @NotNull Integer userId,
			@NotNull HttpServletResponse response) {
		if (!ObjectUtils.allNotNull(logicFileId, userId, response)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		LogicFile logicFile = logicFileRepository.queryByIdAndUserId(logicFileId, userId);
		if (Objects.isNull(logicFile)) {
			throw new NoDataException(0, "no such logic file");
		}
		PhysicalFile physicalFile = physicalFileRepository.queryById(logicFile.getPhysicaFileId());
		if (Objects.isNull(physicalFile)) {
			throw new NoDataException(0, "no such physical file");
		}
		String filePath = physicalFile.getLocation();
		File downloadFile = new File(filePath, physicalFile.getName());
		if (!downloadFile.exists()) {
			throw new NoDataException(0, "no such physical file");
		}
		try {
			FileHandler.transferLocalFileToStream(downloadFile, response.getOutputStream());
		} catch (IOException e) {
			throw new FileTransferException(0, "transfer file to stream fail!");
		}
	}
}
