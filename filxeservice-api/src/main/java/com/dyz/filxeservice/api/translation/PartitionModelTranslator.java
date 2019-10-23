package com.dyz.filxeservice.api.translation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.dyz.filxeservice.api.model.PartitionCreateVo;
import com.dyz.filxeservice.api.model.PartitionInfoVo;
import com.dyz.filxeservice.api.model.PartitionUpdateVo;
import com.dyz.filxeservice.common.constant.ServiceConstant;
import com.dyz.filxeservice.common.execption.IllegalParamException;
import com.dyz.filxeservice.common.util.DateHandler;
import com.dyz.filxeservice.sal.bo.PartitionCreateBO;
import com.dyz.filxeservice.sal.bo.PartitionInfoBo;
import com.dyz.filxeservice.sal.bo.PartitionQueryBo;
import com.dyz.filxeservice.sal.bo.PartitionUpdateBo;

public class PartitionModelTranslator {

	/**
	 * 
	 * @param partitionName
	 * @param userId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static PartitionQueryBo toBo(String partitionName, Integer userId, String fromDate, String toDate) {
		PartitionQueryBo queryBo = null;
		try {
			queryBo = PartitionQueryBo.builder().partitionName(partitionName).userId(userId)
					.fromDate(Objects.isNull(fromDate) ? null
							: DateUtils.parseDate(fromDate, ServiceConstant.DATE_FORMAT_SHORT))
					.toDate(Objects.isNull(toDate) ? null
							: DateUtils.parseDate(toDate, ServiceConstant.DATE_FORMAT_SHORT))
					.build();
		} catch (ParseException e) {
			throw new IllegalParamException(0, "illegal param");
		}
		return queryBo;
	}

	/**
	 * 
	 * @param createVO
	 * @return
	 */
	public static PartitionCreateBO toBo(PartitionCreateVo createVO) {
		if (Objects.isNull(createVO)) {
			return null;
		}
		return PartitionCreateBO.builder().description(createVO.getDescription())
				.partitionName(createVO.getPartitionName()).build();
	}

	/**
	 * 
	 * @param updateVo
	 * @return
	 */
	public static PartitionUpdateBo toBo(PartitionUpdateVo updateVo) {
		if (Objects.isNull(updateVo)) {
			return null;
		}
		return PartitionUpdateBo.builder().description(updateVo.getDescription()).partitionId(updateVo.getPartitionId())
				.partitionName(updateVo.getPartitionName()).build();
	}

	/**
	 * 
	 * @param infoBo
	 * @return
	 */
	public static PartitionInfoVo toVo(PartitionInfoBo infoBo) {
		if (Objects.isNull(infoBo)) {
			return null;
		}
		return PartitionInfoVo.builder().createTime(DateHandler.getDateString(infoBo.getCreateTime()))
				.description(infoBo.getDescription()).isDefault(infoBo.isDefault()).partitionId(infoBo.getPartitionId())
				.partitionName(infoBo.getPartitionName()).userId(infoBo.getUserId()).build();
	}

	/**
	 * 
	 * @param boList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<PartitionInfoVo> toVoList(List<PartitionInfoBo> boList) {
		if (CollectionUtils.isEmpty(boList)) {
			return Collections.EMPTY_LIST;
		}
		List<PartitionInfoVo> result = new ArrayList<>();
		boList.stream().forEach(x -> {
			result.add(toVo(x));
		});
		return result;
	}
}
