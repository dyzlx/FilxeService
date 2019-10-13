package com.dyz.filxeservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dyz.filxeservice.domain.entity.Partition;

@Repository
public interface PartitionRepository extends JpaRepository<Partition, Integer>{

}
