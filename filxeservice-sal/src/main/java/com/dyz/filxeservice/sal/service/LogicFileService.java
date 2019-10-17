package com.dyz.filxeservice.sal.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.dyz.filxeservice.sal.bo.LogicFileInfoBo;
import com.dyz.filxeservice.sal.bo.LogicFileQueryBo;
import com.dyz.filxeservice.sal.bo.LogicFileUpdateBo;

public interface LogicFileService {

	/**
	 * 
	 * @param queryBo
	 * @return
	 */
	List<LogicFileInfoBo> queryLogicFileInfo(@NotNull LogicFileQueryBo queryBo);

	/**
	 * 
	 * @param logicFileId
	 * @param userId
	 */
	void deleteLogicFile(@NotNull Integer logicFileId, @NotNull Integer userId);

	/**
	 * 
	 * @param updateBo
	 */
	void updateLogicFileInfo(@NotNull LogicFileUpdateBo updateBo);
}
