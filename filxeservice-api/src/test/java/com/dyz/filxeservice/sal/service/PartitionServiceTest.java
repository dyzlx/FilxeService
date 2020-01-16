package com.dyz.filxeservice.sal.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dyz.filxeservice.sal.bo.PartitionCreateBO;
import com.dyz.filxeservice.sal.bo.PartitionInfoBo;
import com.dyz.filxeservice.sal.bo.PartitionQueryBo;
import com.dyz.filxeservice.sal.bo.PartitionUpdateBo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartitionServiceTest {

	@Autowired
	private PartitionService partitionService;

	@Test
	public void queryPartitionTest() {
		PartitionQueryBo queryBo = PartitionQueryBo.builder().partitionName(null).userId(1).build();
		List<PartitionInfoBo> result = partitionService.queryPartitionInfo(queryBo);
		System.out.println(result);
	}
}
