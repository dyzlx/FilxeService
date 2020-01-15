package com.dyz.filxeservice.domain.repository;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dyz.filxeservice.common.constant.ServiceConstant;
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
	public void queryTest() throws ParseException {
	    Date fromTime = DateUtils.parseDate("1970-01-01", ServiceConstant.DATE_FORMAT_SHORT);
	    Date toTime = DateUtils.parseDate("9999-01-01", ServiceConstant.DATE_FORMAT_SHORT);
		List<LogicFile> lfList = logicFileRepository.queryLogicFiles(2,null, null, null, fromTime, toTime);
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
