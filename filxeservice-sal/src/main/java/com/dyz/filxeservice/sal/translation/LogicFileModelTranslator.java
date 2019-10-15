package com.dyz.filxeservice.sal.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import com.dyz.filxeservice.common.util.DateHandler;
import com.dyz.filxeservice.domain.entity.LogicFile;
import com.dyz.filxeservice.sal.bo.LogicFileInfoBo;

public class LogicFileModelTranslator {
	
	public static LogicFileInfoBo toBo(LogicFile entity)
	{
		if(Objects.isNull(entity))
		{
			return null;
		}
		return LogicFileInfoBo.builder()
				.logicFileName(entity.getName())
				.partitionId(entity.getPartitionId())
				.createTime(DateHandler.getDateString(entity.getCreateTime()))
				.ishared(String.valueOf(entity.isShared()))
				.userId(entity.getUserId())
				.build();
	}
	
	public static List<LogicFileInfoBo> toBoList(List<LogicFile> entitys)
	{
		if(CollectionUtils.isEmpty(entitys))
		{
			return Collections.emptyList();
		}
		List<LogicFileInfoBo> result = new ArrayList<>();
		entitys.stream().forEach(x->{
			result.add(toBo(x));
		});
		return result;
	}

}
