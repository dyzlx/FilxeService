package com.dyz.filxeservice.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dyz.filxeservice.domain.entity.LogicFile;

@Repository
public interface LogicFileRepository extends JpaRepository<LogicFile, Integer> {

	/**
	 * query by logic file name (this can Fuzzy query), partition id and is-shared
	 * 
	 * @return
	 */
	@Query(value = "select * from logicfile where if(?1 is NULL,1=1,id=?1)"
            + " and if(?2 is NULL,1=1,name like %?2%)"
			+ " and if(?3 is NULL,1=1,partition_id=?3)"
			+ " and if(?4 is NULL,1=1,user_id=?4)"
            + " and create_time between ?5 and ?6"
			+ " and if(?7 is null,1=1,is_shared=?7)", nativeQuery = true)
	List<LogicFile> queryLogicFiles(Integer logicFileId, String logicFileName, Integer partitionId,
                                    Integer userId, Date fromDate, Date toDate, String shared);

	List<LogicFile> queryByPhysicaFileId(Integer id);
	
	List<LogicFile> queryByPartitionId(Integer id);

	LogicFile queryByIdAndUserId(Integer id, Integer userId);

	LogicFile queryById(Integer id);
}
