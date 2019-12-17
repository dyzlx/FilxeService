package com.dyz.filxeservice.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dyz.filxeservice.domain.entity.Partition;

@Repository
public interface PartitionRepository extends JpaRepository<Partition, Integer>{
	
	@Query(value = "select * from fpartition where if(?1 is NULL,1=1,id=?1)"
            + " and if(?2 is NULL,1=1,name like %?2%)"
			+ " and if(?3 is NULL,1=1,user_id=?3)"
			+ " and create_time between ?4 and ?5", nativeQuery = true)
	List<Partition> queryPartitions(Integer partitionId, String partitionName, Integer userId, Date fromDate, Date toDate);

	Partition queryById(Integer id);
	
	Partition queryByIdAndUserId(Integer id, Integer userId);
	
	List<Partition> queryByIsDefaultAndUserId(boolean isDefault, Integer userId);

}
