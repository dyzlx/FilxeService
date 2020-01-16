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
	List<PartitionInfoBo> queryPartitionInfo(PartitionQueryBo queryBo);
	
	/**
	 * 
	 * @param createBo
	 * @return
	 */
	Integer createPartition(PartitionCreateBO createBo);

	/**
	 *
	 * @param updateBo
	 */
	void updatePartition(PartitionUpdateBo updateBo);

	/**
	 *
	 * @param partitionId
	 */
	void deletePartition(Integer partitionId);
	
}
