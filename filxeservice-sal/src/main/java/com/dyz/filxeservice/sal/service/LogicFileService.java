package com.dyz.filxeservice.sal.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.dyz.filxeservice.sal.bo.LogicFileInfoBo;
import com.dyz.filxeservice.sal.bo.LogicFileQueryBo;
import com.dyz.filxeservice.sal.bo.LogicFileUpdateBo;
import com.dyz.filxeservice.sal.bo.LogicFileUploadBo;
import com.dyz.filxeservice.sal.bo.MultipleFileDowloadBo;

public interface LogicFileService {

	/**
	 * query logic file by query object
	 * 
	 * @param queryBo
	 * @return
	 */
	List<LogicFileInfoBo> queryLogicFileInfo(@NotNull LogicFileQueryBo queryBo);

	/**
	 * delete logic file by logic file id and user id
	 * 
	 * @param logicFileId
	 * @param userId
	 */
	void deleteLogicFile(@NotNull Integer logicFileId, @NotNull Integer userId);

	/**
	 * delete logic files multiple logic file delete
	 * 
	 * @param logicFileIds
	 * @param userId
	 */
	void deleteLogicFiles(@NotNull List<Integer> logicFileIds, @NotNull Integer userId);

	/**
	 * update logic file info by logic file id and update info
	 * 
	 * @param updateBo
	 */
	void updateLogicFileInfo(@NotNull LogicFileUpdateBo updateBo, @NotNull Integer userId);

	/**
	 * upload file
	 * 
	 * @param file
	 * @param uploadBo
	 * @param userId
	 * @return
	 */
	Integer uploadFile(@NotNull MultipartFile file, @NotNull LogicFileUploadBo uploadBo, @NotNull Integer userId);

	/**
	 * upload files. multiple files upload, these files will belong to same
	 * partition
	 * 
	 * @param files
	 * @param uploadBo
	 * @param userId
	 * @return
	 */
	List<Integer> uploadFiles(@NotNull MultipartFile[] files, @NotNull LogicFileUploadBo uploadBo,
			@NotNull Integer userId);

	/**
	 * download file
	 * 
	 * @param logicFileId
	 * @param userId
	 * @param response
	 */
	void downloadFile(@NotNull Integer logicFileId, @NotNull Integer userId, @NotNull HttpServletResponse response);

	/**
	 * download files. multiple files download. these files will compress into
	 * compressed files. client will get a *.zip file
	 * 
	 * @param downloadBo
	 * @param userId
	 * @param response
	 */
	void downloadAsZip(@NotNull MultipleFileDowloadBo downloadBo, @NotNull Integer userId,
			@NotNull HttpServletResponse response);
}
