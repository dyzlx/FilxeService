package com.dyz.filxeservice.sal.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dyz.filxeservice.sal.bo.LogicFileInfoBo;
import com.dyz.filxeservice.sal.bo.LogicFileQueryBo;
import com.dyz.filxeservice.sal.bo.LogicFileUpdateBo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicFileServiceTest {

	@Autowired
	private LogicFileService logicFileService;

	@Test
	public void queryLogicFileInfoTest() {
		LogicFileQueryBo query = LogicFileQueryBo.builder().userId(1).build();
		List<LogicFileInfoBo> result = logicFileService.queryLogicFileInfo(query);
		System.out.println(result);
	}

	@Test
	public void updateLogicFileInfoTest() {
		LogicFileUpdateBo updateBo = LogicFileUpdateBo.builder().ishared(true).logicFileId(6)
				.logicFileName("update_test").partitionId(3).build();
		logicFileService.updateLogicFileInfo(updateBo, 1);
	}
	
	@Test
	public void deleteLogicFileInfoTest() {
		logicFileService.deleteLogicFile(6, 1);
	}
}
