package com.dyz.filxeservice.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dyz.filxeservice.domain.entity.LogicFile;

@Repository
public interface LogicFileRepository extends JpaRepository<LogicFile, Integer> {

	@Query(value = "select * from logicfile", nativeQuery = true)
	List<LogicFile> queryLogicFiles(String logicFileName, Date fromTime, Date toTime);
}
