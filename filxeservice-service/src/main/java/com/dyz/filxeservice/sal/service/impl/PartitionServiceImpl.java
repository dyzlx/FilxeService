package com.dyz.filxeservice.sal.service.impl;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyz.filxeservice.domain.repository.PartitionRepository;
import com.dyz.filxeservice.sal.bo.PartitionInfoBo;
import com.dyz.filxeservice.sal.bo.PartitionQueryBo;
import com.dyz.filxeservice.sal.service.PartitionService;

@Service
public class PartitionServiceImpl implements PartitionService {
	
	@Autowired
	private PartitionRepository partitionRepository;

	@Override
	public List<PartitionInfoBo> queryPartitionInfo(@NotNull PartitionQueryBo queryBo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createPartition(@NotNull String partitionName, String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePartition(String partitionName, String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePartition(@NotNull String partitionName) {
		// TODO Auto-generated method stub
		
	}

}
