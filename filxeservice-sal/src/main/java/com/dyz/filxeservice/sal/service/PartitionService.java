package com.dyz.filxeservice.sal.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.dyz.filxeservice.sal.bo.PartitionCreateBO;
import com.dyz.filxeservice.sal.bo.PartitionInfoBo;
import com.dyz.filxeservice.sal.bo.PartitionQueryBo;
import com.dyz.filxeservice.sal.bo.PartitionUpdateBo;

public interface PartitionService {

	/**
	 * 
	 * @param queryBo
	 * @return
	 */
	List<PartitionInfoBo> queryPartitionInfo(@NotNull PartitionQueryBo queryBo);
	
	/**
	 * 
	 * @param createBo
	 * @param userId
	 * @return
	 */
	Integer createPartition(@NotNull PartitionCreateBO createBo, @NotNull Integer userId);
	
	/**
	 * 
	 * @param partitionName
	 * @param description
	 */
	void updatePartition(@NotNull PartitionUpdateBo updateBo, @NotNull Integer userId);
	
	/**
	 * 
	 * @param partitionName
	 */
	void deletePartition(@NotNull Integer partitionId, @NotNull Integer userId);
	
}
