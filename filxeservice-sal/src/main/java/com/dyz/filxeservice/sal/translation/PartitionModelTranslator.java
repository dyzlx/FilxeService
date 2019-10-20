package com.dyz.filxeservice.sal.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import com.dyz.filxeservice.domain.entity.Partition;
import com.dyz.filxeservice.sal.bo.PartitionInfoBo;

public class PartitionModelTranslator {

	public static PartitionInfoBo toBo(Partition entity) {
		if (Objects.isNull(entity)) {
			return null;
		}
		return PartitionInfoBo.builder().createTime(entity.getCreateTime()).description(entity.getDescription())
				.partitionId(entity.getId()).partitionName(entity.getName()).userId(entity.getUserId()).build();
	}

	public static List<PartitionInfoBo> toBoList(List<Partition> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return Collections.emptyList();
		}
		List<PartitionInfoBo> result = new ArrayList<>();
		entitys.stream().forEach(x -> {
			result.add(toBo(x));
		});
		return result;
	}

}
