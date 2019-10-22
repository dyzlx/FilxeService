package com.dyz.filxeservice.api.translation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.dyz.filxeservice.api.model.LogicFileInfoVo;
import com.dyz.filxeservice.api.model.LogicFileUpdateVo;
import com.dyz.filxeservice.common.constant.ServiceConstant;
import com.dyz.filxeservice.common.execption.IllegalParamException;
import com.dyz.filxeservice.common.util.DateHandler;
import com.dyz.filxeservice.sal.bo.LogicFileInfoBo;
import com.dyz.filxeservice.sal.bo.LogicFileQueryBo;
import com.dyz.filxeservice.sal.bo.LogicFileUpdateBo;
import com.dyz.filxeservice.sal.bo.LogicFileUploadBo;

public class LogicFileModelTranslator {

	/**
	 * 
	 * @param logicFileName
	 * @param ishared
	 * @param partitionId
	 * @param userId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static LogicFileQueryBo toBo(String logicFileName, String ishared, Integer partitionId, Integer userId,
			String fromDate, String toDate) {
		LogicFileQueryBo queryBo = null;
		try {
			queryBo = LogicFileQueryBo.builder().logicFileName(logicFileName)
					.ishared(Objects.isNull(ishared) ? null : Boolean.valueOf(ishared)).partitionId(partitionId)
					.userId(userId)
					.fromTime(Objects.isNull(fromDate) ? null
							: DateUtils.parseDate(fromDate, ServiceConstant.DATE_FORMAT_SHORT))
					.toTime(Objects.isNull(toDate) ? null
							: DateUtils.parseDate(toDate, ServiceConstant.DATE_FORMAT_SHORT))
					.build();
		} catch (ParseException e) {
			throw new IllegalParamException(0, "illegal param");
		}
		return queryBo;
	}

	/**
	 * 
	 * @param partitionId
	 * @param ishared
	 * @return
	 */
	public static LogicFileUploadBo toBo(Integer partitionId, boolean ishared) {
		return LogicFileUploadBo.builder().ishared(ishared).partitionId(partitionId).build();
	}

	/**
	 * 
	 * @param vo
	 * @return
	 */
	public static LogicFileUpdateBo toBo(LogicFileUpdateVo vo) {
		if (Objects.isNull(vo)) {
			return null;
		}
		return LogicFileUpdateBo.builder().ishared(vo.isIshared()).logicFileId(vo.getLogicFileId())
				.logicFileName(vo.getLogicFileName()).partitionId(vo.getPartitionId()).build();
	}

	/**
	 * 
	 * @param infoBo
	 * @return
	 */
	public static LogicFileInfoVo toVo(LogicFileInfoBo infoBo) {
		if (Objects.isNull(infoBo)) {
			return null;
		}
		return LogicFileInfoVo.builder().logicFileId(infoBo.getLogicFileId()).logicFileName(infoBo.getLogicFileName())
				.ishared(infoBo.isIshared()).partitionId(infoBo.getPartitionId()).userId(infoBo.getUserId())
				.createTime(DateHandler.getDateString(infoBo.getCreateTime())).build();
	}

	/**
	 * 
	 * @param boList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<LogicFileInfoVo> toVoList(List<LogicFileInfoBo> boList) {
		if (CollectionUtils.isEmpty(boList)) {
			return Collections.EMPTY_LIST;
		}
		List<LogicFileInfoVo> result = new ArrayList<>();
		boList.stream().forEach(x -> {
			result.add(toVo(x));
		});
		return result;
	}

}
