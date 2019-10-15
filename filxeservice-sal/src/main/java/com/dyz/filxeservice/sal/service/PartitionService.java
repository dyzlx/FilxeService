package com.dyz.filxeservice.sal.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.dyz.filxeservice.sal.bo.PartitionInfoBo;
import com.dyz.filxeservice.sal.bo.PartitionQueryBo;

public interface PartitionService {

	/**
	 * 
	 * @param queryBo
	 * @return
	 */
	List<PartitionInfoBo> queryPartitionInfo(@NotNull PartitionQueryBo queryBo);
	
	/**
	 * 
	 * @param partitionName
	 * @param description
	 */
	void createPartition(@NotNull String partitionName, String description);
	
	/**
	 * 
	 * @param partitionName
	 * @param description
	 */
	void updatePartition(String partitionName, String description);
	
	/**
	 * 
	 * @param partitionName
	 */
	void deletePartition(@NotNull String partitionName);
	
}
