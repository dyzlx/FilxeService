package com.dyz.filxeservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dyz.filxeservice.domain.entity.LogicFileComment;

@Repository
public interface LogicFileCommentRepository extends JpaRepository<LogicFileComment, Integer> {

}
