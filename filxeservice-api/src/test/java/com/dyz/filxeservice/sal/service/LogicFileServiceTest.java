package com.dyz.filxeservice.sal.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dyz.filxeservice.sal.bo.LogicFileInfoBo;
import com.dyz.filxeservice.sal.bo.LogicFileQueryBo;

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
}
