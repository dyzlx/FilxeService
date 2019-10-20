package com.dyz.filxeservice.domain.repository;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dyz.filxeservice.domain.entity.Partition;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartitionRepositoryTest {

	@Autowired
	private PartitionRepository partitionRepository;

	@Test
	public void addTest() {
		Partition partition = Partition.builder().createTime(new Date()).description("spring cloud note").name("note")
				.userId(1).build();
		partitionRepository.save(partition);
	}
	
	@Test
	public void queryTest() {
		List<Partition> result = partitionRepository.queryPartitions("test", null, null, null);
		System.out.println(result);
	}
}
