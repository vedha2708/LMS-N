package com.exam.portal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exam.portal.Model.Assignment;

import jakarta.transaction.Transactional;

public interface AssignmentRepo extends JpaRepository<Assignment, Integer> {
	
	@Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM assignment WHERE assignmentid IN(?1)")
    void delass(List<String> assignmentid);

}
