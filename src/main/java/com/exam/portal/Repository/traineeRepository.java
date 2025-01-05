package com.exam.portal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.exam.portal.Model.Program;
import com.exam.portal.Model.trainee;

import java.util.List;

public interface traineeRepository extends JpaRepository<trainee, Integer> {

    @Query(value = "select * from trainee where email = ?1", nativeQuery = true)
    trainee findByEmail(String email);

  
    
    List<trainee> findByProgram(Program program);
    
    List<trainee> findByOrganizationId(Integer organizationId);
}
