package com.dyz.filxeservice.domain.repository;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dyz.filxeservice.domain.entity.PhysicalFile;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhysicalFileRepositoryTest {

	@Autowired
	private PhysicalFileRepository physicalFileRepository;

	@Test
	public void addTest() {
		PhysicalFile py = PhysicalFile.builder().location("/dyz/test").md5("sd7fsdhf7s8d7f").name("touch.lock")
				.size(34765).type("lock").uploadTime(new Date()).build();
		physicalFileRepository.save(py);
	}

	@Test
	public void queryByIdTest() {
		PhysicalFile py = physicalFileRepository.queryById(1);
		System.out.println(py);
	}
}
