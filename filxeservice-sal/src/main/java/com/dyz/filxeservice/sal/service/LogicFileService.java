package com.dyz.filxeservice.sal.service;

import com.dyz.filxeservice.sal.bo.LogicFileInfoBo;
import com.dyz.filxeservice.sal.bo.LogicFileQueryBo;
import com.dyz.filxeservice.sal.bo.LogicFileUpdateBo;
import com.dyz.filxeservice.sal.bo.LogicFileUploadBo;
import com.dyz.filxeservice.sal.bo.MultipleFileDowloadBo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface LogicFileService {

	/**
	 * query logic file by query object
	 * 
	 * @param queryBo
	 * @return
	 */
	List<LogicFileInfoBo> queryLogicFileInfo(LogicFileQueryBo queryBo);

	/**
	 * delete logic file by logic file id and user id
	 * 
	 * @param logicFileId
	 */
	void deleteLogicFile(Integer logicFileId);

	/**
	 * delete logic files multiple logic file delete
	 * 
	 * @param logicFileIds
	 */
	void deleteLogicFiles(List<Integer> logicFileIds);

	/**
	 * update logic file info by logic file id and update info
	 * 
	 * @param updateBo
	 */
	void updateLogicFileInfo(LogicFileUpdateBo updateBo);

	/**
	 * upload file
	 * 
	 * @param file
	 * @param uploadBo
	 * @return
	 */
	Integer uploadFile(MultipartFile file, LogicFileUploadBo uploadBo);

	/**
	 * upload files. multiple files upload, these files will belong to same
	 * partition
	 * 
	 * @param files
	 * @param uploadBo
	 * @return
	 */
	List<Integer> uploadFiles(MultipartFile[] files, LogicFileUploadBo uploadBo);

	/**
	 * download file
	 * 
	 * @param logicFileId
	 * @param response
	 */
	void downloadFile(Integer logicFileId, HttpServletResponse response);

	/**
	 * download files. multiple files download. these files will compress into
	 * compressed files. client will get a *.zip file
	 * 
	 * @param downloadBo
	 * @param response
	 */
	void downloadAsZip(MultipleFileDowloadBo downloadBo, HttpServletResponse response);
}
