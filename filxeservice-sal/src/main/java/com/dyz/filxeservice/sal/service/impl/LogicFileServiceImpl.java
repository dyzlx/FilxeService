package com.dyz.filxeservice.sal.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dyz.filxeservice.common.constant.ServiceConstant;
import com.dyz.filxeservice.common.execption.FileTransferException;
import com.dyz.filxeservice.common.execption.IllegalOperationException;
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
import com.dyz.filxeservice.sal.bo.MultipleFileDowloadBo;
import com.dyz.filxeservice.sal.service.LogicFileService;
import com.dyz.filxeservice.sal.translation.LogicFileModelTranslator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LogicFileServiceImpl implements LogicFileService {

	@Value("${filxeservice.file.store.path}")
	private String LOCAL_STORE_PATH;

	@Value("${filxeservice.file.store.temp-path}")
	private String LOCAL_TEMP_STORE_PATH;

	@Autowired
	private LogicFileRepository logicFileRepository;

	@Autowired
	private PartitionRepository partitionRepository;

	@Autowired
	private PhysicalFileRepository physicalFileRepository;

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public List<LogicFileInfoBo> queryLogicFileInfo(@NotNull LogicFileQueryBo queryBo) {
		log.info("begin to query logicfile. queryBo = {}", queryBo);
		if (Objects.isNull(queryBo)) {
			log.error("param querybo is null!");
			throw new IllegalParamException(0, "param can not be null");
		}
        List<LogicFile> entityList = logicFileRepository
                .queryLogicFiles(queryBo.getLogicFileId(), queryBo.getLogicFileName(), queryBo.getPartitionId(), queryBo.getUserId(),
                        queryBo.getFromTime(), queryBo.getToTime())
                .stream()
                .filter(x -> Objects.isNull(queryBo.getIshared()) ? true : Objects.equals(queryBo.getIshared(), x.isShared()))
                .collect(Collectors.toList());
        log.info("end of query logicfile. result = {}", entityList);
		return LogicFileModelTranslator.toBoList(entityList);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void deleteLogicFile(@NotNull Integer logicFileId, @NotNull Integer userId) {
		log.info("begin to delete logicfile, logicFileId = {}, userId = {}", logicFileId, userId);
		if (!ObjectUtils.allNotNull(logicFileId, userId)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		LogicFile logicFile = logicFileRepository.queryByIdAndUserId(logicFileId, userId);
		if (Objects.isNull(logicFile)) {
			log.error("no such logicfile");
			throw new NoDataException(0, "no such logic file");
		}
		int physicalFileId = logicFile.getPhysicaFileId();
		logicFileRepository.delete(logicFile);
		log.info("logicfile has deleted, {}", logicFile);
		List<LogicFile> others = logicFileRepository.queryByPhysicaFileId(physicalFileId);
		if (CollectionUtils.isEmpty(others)) {
			log.info("its physical file no other logicfile reference, delete physical file");
			deletePhysicalFileAndLocalFile(physicalFileId);
		}
		log.info("end of delete logicfile");
	}

	@Override
	public void deleteLogicFiles(@NotNull List<Integer> logicFileIds, @NotNull Integer userId) {
		log.info("begin to multiple logicFiles delete, ids = {}, userId = {}", logicFileIds, userId);
		if (Objects.isNull(logicFileIds)) {
			throw new IllegalParamException(0, "param is null");
		}
		for (Integer id : logicFileIds) {
			deleteLogicFile(id, userId);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateLogicFileInfo(@NotNull LogicFileUpdateBo updateBo, @NotNull Integer userId) {
		log.info("begin to update logicfile info, updateBo = {}, userId = {}", updateBo, userId);
		if (Objects.isNull(updateBo)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		Integer logicFileId = updateBo.getLogicFileId();
		Integer partitionId = updateBo.getPartitionId();
		LogicFile updatedLogicFile = logicFileRepository.queryByIdAndUserId(logicFileId, userId);
		Partition partotion = partitionRepository.queryByIdAndUserId(partitionId, userId);
		if (!ObjectUtils.allNotNull(updatedLogicFile, partotion)) {
			log.error("no such logicfile or partition, logicFileId = {}, partitionId = {}", logicFileId, partitionId);
			throw new NoDataException(0, "no such logic file or partition");
		}
		updatedLogicFile.setName(updateBo.getLogicFileName());
		updatedLogicFile.setPartitionId(partotion.getId());
		updatedLogicFile.setShared(updateBo.isIshared());
		logicFileRepository.save(updatedLogicFile);
		log.info("end of update logicfile info, updated logicfile info = {}", updatedLogicFile);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public Integer uploadFile(@NotNull MultipartFile file, @NotNull LogicFileUploadBo uploadBo,
			@NotNull Integer userId) {
		log.info("begin to upload file, uploadBo = {}, userId = {}", uploadBo, userId);
		if (!ObjectUtils.allNotNull(file, uploadBo, userId)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		// TO-DO:same file not store this
		String originFileName = file.getOriginalFilename();
		String localFileName = UUID.randomUUID().toString() + originFileName;
		File localFile = FileHandler.transferToLocalFile(file, LOCAL_STORE_PATH, localFileName);
		log.info("origin file {} has transfer to local file {}", originFileName, localFile.getAbsolutePath());
		String localFileType = getFileTypeFromFileName(localFileName);
		PhysicalFile physicalFile = PhysicalFile.builder().location(LOCAL_STORE_PATH).name(localFileName)
				.size(localFile.length()).type(localFileType).uploadTime(new Date()).build();
		physicalFileRepository.save(physicalFile);
		log.info("physical file info has saved, physicalFile = {}", physicalFile);
		Integer physicalFileId = physicalFile.getId();
		Integer partitionId = uploadBo.getPartitionId();
		if (Objects.isNull(partitionId)) {
			log.info("partitionId is null, this logic file will belong default partition");
			List<Partition> defaultPartitions = partitionRepository.queryByIsDefaultAndUserId(true, userId);
			if (CollectionUtils.isEmpty(defaultPartitions)) {
				log.warn("no default partition found, create a default partition");
				Partition defaultPartition = Partition.builder().createTime(new Date()).isDefault(true).name("Default")
						.userId(userId).build();
				partitionRepository.save(defaultPartition);
				log.info("default partition has created, defaultPartition = {}", defaultPartition);
				defaultPartitions.add(defaultPartition);
			}
			partitionId = defaultPartitions.get(0).getId();
		}
		LogicFile newLogicFile = LogicFile.builder().createTime(new Date()).isShared(uploadBo.isIshared())
				.name(originFileName).partitionId(partitionId).physicaFileId(physicalFileId).userId(userId).build();
		logicFileRepository.save(newLogicFile);
		log.info("end of upload file, logicfile info has saved, logicFile = {}", newLogicFile);
		return newLogicFile.getId();
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	@Override
	public List<Integer> uploadFiles(@NotNull MultipartFile[] files, @NotNull LogicFileUploadBo uploadBo,
			@NotNull Integer userId) {
		if (Objects.isNull(files)) {
			throw new IllegalParamException(0, "param is null");
		}
		log.info("begin to multiple logicfiles upload, files count = {}, userId = {}", files.length, userId);
		List<Integer> resultIds = new ArrayList<>();
		for (MultipartFile file : files) {
			resultIds.add(uploadFile(file, uploadBo, userId));
		}
		return resultIds;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void downloadFile(@NotNull Integer logicFileId, @NotNull Integer userId,
			@NotNull HttpServletResponse response) {
		log.info("begin to download file, logicFileId = {}, userId = {}", logicFileId, userId);
		if (!ObjectUtils.allNotNull(logicFileId, userId, response)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		LogicFile logicFile = logicFileRepository.queryById(logicFileId);
		if (Objects.isNull(logicFile)) {
			log.error("no such logic file, id = {}, userId = {}", logicFileId, userId);
			throw new NoDataException(0, "no such local file, id = " + logicFileId);
		}
		File downloadFile = getDownloadedFileByLogicFileAndUserId(logicFile, userId);
		setDownloadFileToResponse(downloadFile, response, logicFile.getName());
		log.info("end of download file, fileName = {}", logicFile.getName());
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void downloadAsZip(@NotNull MultipleFileDowloadBo downloadBo, @NotNull Integer userId,
			@NotNull HttpServletResponse response) {
		log.info("begin to multiple file download, bo = {}, userId = {}", downloadBo, userId);
		if (!ObjectUtils.allNotNull(downloadBo, downloadBo.getLogicFileIds(), userId, response)) {
			throw new IllegalParamException(0, "param can not be null");
		}
		String resultZipFileName = downloadBo.getCompressionFileName();
		if (StringUtils.isBlank(resultZipFileName)) {
			resultZipFileName = UUID.randomUUID().toString();
		}
		List<File> uncompressionFiles = new ArrayList<>();
		downloadBo.getLogicFileIds().stream().forEach(x -> {
			LogicFile logicFile = logicFileRepository.queryById(x);
			if (Objects.isNull(logicFile)) {
				log.error("no such logic file, id = {}, userId = {}", x, userId);
				throw new NoDataException(0, "no such local file, id = " + x);
			}
			uncompressionFiles.add(getDownloadedFileByLogicFileAndUserId(logicFile, userId));
		});
		log.info("get uncompression file set, file count = {}, set = {}", uncompressionFiles.size(),
				uncompressionFiles);
		File resultCompressFile = new File(LOCAL_TEMP_STORE_PATH, resultZipFileName);
		log.info("target files will compress into temp file {}", resultCompressFile.getAbsolutePath());
		FileHandler.zip(uncompressionFiles, resultCompressFile);
		if (!resultCompressFile.exists()) {
			log.error("file compression fail, result file not exist");
			throw new NoDataException(0, "file compression fail, result file not exist");
		}
		log.info("target files compress success, path = {}", resultCompressFile.getAbsolutePath());
		setDownloadFileToResponse(resultCompressFile, response);
		log.info("delete local zip file");
		resultCompressFile.delete();
		log.info("end of multiple file download, fileName = {}", resultCompressFile.getName());
	}
	
	/**
	 * 
	 * @param localFileName
	 * @return
	 */
	private String getFileTypeFromFileName(String localFileName) {
		if(Objects.isNull(localFileName)) {
			return null;
		}
		String[] localFileNameStrs = localFileName.split(ServiceConstant.STRING_SPLITE_POINT);
		String localFileType = localFileNameStrs[localFileNameStrs.length - 1];
		return localFileType;
	}

	/**
	 * 
	 * @param physicalFileId
	 */
	private void deletePhysicalFileAndLocalFile(Integer physicalFileId) {
		PhysicalFile physicalFile = physicalFileRepository.queryById(physicalFileId);
		if (Objects.isNull(physicalFile)) {
			log.error("no such physicalfile, physicalFileId = {}", physicalFileId);
			throw new NoDataException(0, "no such physical file");
		}
		log.info("begin to delete physical file {}", physicalFile);
		File file = new File(physicalFile.getLocation(), physicalFile.getName());
		if (!file.exists()) {
			physicalFileRepository.delete(physicalFile);
			log.warn("local file {} not exists, just delete physical file object", file.getAbsolutePath());
			return;
		}
		if (file.isDirectory() || !file.delete()) {
			log.error("local file {} delete fail", file.getAbsoluteFile());
			return;
		}
		physicalFileRepository.delete(physicalFile);
		log.info("physical file and local file delete success.");
	}

	/**
	 * get resource file and check downloaded permissions
	 * 
	 * @param logicFile
	 * @param userId
	 * @return
	 */
	private File getDownloadedFileByLogicFileAndUserId(LogicFile logicFile, Integer userId) {
		if (!ObjectUtils.allNotNull(logicFile, userId)) {
			return null;
		}
		if (!checkDownloadFiles(logicFile, userId)) {
			log.error("download fail, this file can not downloaded by user = {}", userId);
			throw new IllegalOperationException(0, "this logic file is not shared");
		}
		PhysicalFile physicalFile = physicalFileRepository.queryById(logicFile.getPhysicaFileId());
		if (Objects.isNull(physicalFile)) {
			log.error("no such physical file, physicaFileId = {}", logicFile.getPhysicaFileId());
			throw new NoDataException(0, "no such physical file");
		}
		String filePath = physicalFile.getLocation();
		File downloadFile = new File(filePath, physicalFile.getName());
		if (!downloadFile.exists()) {
			log.error("local file {} is not exist", filePath + File.separator + physicalFile.getName());
			throw new NoDataException(0, "no such local file");
		}
		return downloadFile;
	}

	/**
	 * The file can be downloaded in the following circumstances. 1) file is shared.
	 * 2) file is belong to current user. 3) file is in default partition.
	 * 
	 * @param logicFile
	 * @param userId
	 * @return
	 */
	private boolean checkDownloadFiles(LogicFile logicFile, Integer userId) {
		if (!ObjectUtils.allNotNull(logicFile, userId)) {
			return false;
		}
		if (logicFile.isShared()) {
			return true;
		}
		if (userId.equals(logicFile.getUserId())) {
			return true;
		}
		Partition partition = partitionRepository.queryById(logicFile.getPartitionId());
		if (partition.isDefault()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param file
	 * @param response
	 */
	private void setDownloadFileToResponse(File file, HttpServletResponse response) {
		setDownloadFileToResponse(file, response, file.getName());
	}

	/**
	 * 
	 * @param file
	 * @param response
	 */
	private void setDownloadFileToResponse(File file, HttpServletResponse response, String downloadFileName) {
		response.setHeader(ServiceConstant.HTTP_HEADER_CONTENT_DISPOSITION,
				ServiceConstant.CONTENT_DISPOSITION_VALUE + downloadFileName);
		try {
			FileHandler.transferLocalFileToStream(file, response.getOutputStream());
		} catch (IOException e) {
			throw new FileTransferException(0, "transfer file to stream fail!");
		}
		log.info("set download file to http response finish");
	}

}
