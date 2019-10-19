package com.dyz.filxeservice.domain.repository;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dyz.filxeservice.domain.entity.LogicFile;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicFileRepositoryTest {

	@Autowired
	private LogicFileRepository logicFileRepository;

	@Test
	public void addTest() {
		LogicFile lf = LogicFile.builder().isShared(true).name("test_dyz").partitionId(2).physicaFileId(2)
				.createTime(new Date()).userId(1).build();
		logicFileRepository.save(lf);
		System.out.println(lf.getId());
	}

	@Test
	public void queryTest() {
		List<LogicFile> lfList = logicFileRepository.queryLogicFiles(null, null, null, null);
		System.out.println(lfList.size());
	}

	@Test
	public void queryByNUllIdTest1() {
		List<LogicFile> lfList = logicFileRepository.findAllById(null);
		System.out.println(lfList);
	}
	
	@Test
	public void queryByNUllIdTest2() {
		LogicFile lfList = logicFileRepository.queryById(null);
		System.out.println(lfList);
	}
}
