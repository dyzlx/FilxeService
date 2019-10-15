package com.dyz.filxeservice.sal.service.impl;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dyz.filxeservice.domain.repository.LogicFileRepository;
import com.dyz.filxeservice.domain.repository.PartitionRepository;
import com.dyz.filxeservice.domain.repository.PhysicalFileRepository;
import com.dyz.filxeservice.sal.bo.LogicFileInfoBo;
import com.dyz.filxeservice.sal.bo.LogicFileQueryBo;
import com.dyz.filxeservice.sal.bo.LogicFileUpdateBo;
import com.dyz.filxeservice.sal.service.LogicFileService;


@Service
public class LogicFileServiceImpl implements LogicFileService {
	
	@Autowired
	private LogicFileRepository logicFileRepository;
	
	@Autowired
	private PartitionRepository partitionRepository;
	
	@Autowired
	private PhysicalFileRepository physicalFileRepository;

	@Override
	public List<LogicFileInfoBo> queryLogicFileInfo(@NotNull LogicFileQueryBo queryBo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLogicFile(@NotNull String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLogicFileInfo(@NotNull LogicFileUpdateBo updateBo) {
		// TODO Auto-generated method stub
		
	}

}
