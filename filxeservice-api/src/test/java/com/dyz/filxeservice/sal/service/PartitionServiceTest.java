package com.dyz.filxeservice.sal.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
	
	@Test
	public void addPartitionTest() {
		partitionService.createPartition("lx_document", "Mrs Xin file folder", 2);
	}
	
	@Test
	public void updatePartitionTest() {
		PartitionUpdateBo updateBo = PartitionUpdateBo.builder().description("update partition test")
				.partitionId(1).partitionName("update").build();
		partitionService.updatePartition(updateBo, 1);
	}
	
	@Test
	public void deletePartitionTest() {
		partitionService.deletePartition(2, 1);
	}
}
