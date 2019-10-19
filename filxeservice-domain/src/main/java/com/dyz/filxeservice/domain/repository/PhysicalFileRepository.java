package com.dyz.filxeservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dyz.filxeservice.domain.entity.PhysicalFile;

@Repository
public interface PhysicalFileRepository extends JpaRepository<PhysicalFile, Integer> {

	PhysicalFile queryById(Integer id);
}
