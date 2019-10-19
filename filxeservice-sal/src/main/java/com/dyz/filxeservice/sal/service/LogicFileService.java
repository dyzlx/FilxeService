package com.dyz.filxeservice.sal.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.dyz.filxeservice.sal.bo.LogicFileInfoBo;
import com.dyz.filxeservice.sal.bo.LogicFileQueryBo;
import com.dyz.filxeservice.sal.bo.LogicFileUpdateBo;
import com.dyz.filxeservice.sal.bo.LogicFileUploadBo;

public interface LogicFileService {

	/**
	 * query logic file by query object
	 * @param queryBo
	 * @return
	 */
	List<LogicFileInfoBo> queryLogicFileInfo(@NotNull LogicFileQueryBo queryBo);

	/**
	 * delete logic file by logic file id and user id
	 * @param logicFileId
	 * @param userId
	 */
	void deleteLogicFile(@NotNull Integer logicFileId, @NotNull Integer userId);

	/**
	 * update logic file info by logic file id and update info
	 * @param updateBo
	 */
	void updateLogicFileInfo(@NotNull LogicFileUpdateBo updateBo, @NotNull Integer userId);
	
	/**
	 * upload file
	 * @param file
	 * @param uploadBo
	 */
	void uploadFile(@NotNull MultipartFile file, @NotNull LogicFileUploadBo uploadBo, @NotNull Integer userId);
	
	/**
	 * download file
	 * @param logicFileId
	 * @param userId
	 * @param response
	 */
	void downloadFile(@NotNull Integer logicFileId, @NotNull Integer userId, @NotNull HttpServletResponse response);
}
