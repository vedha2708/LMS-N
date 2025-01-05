package com.exam.portal.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.mapping.Embedded.Nullable;

import com.exam.portal.Model.Program;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;



public interface ProgramRepository extends JpaRepository<Program, Integer> {

    Optional<Program> findById(Integer id);

    Object findByOrganizationid(int organizationid);
}
