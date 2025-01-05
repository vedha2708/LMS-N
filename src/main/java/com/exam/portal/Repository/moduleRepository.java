package com.exam.portal.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.Model.Topic;
import com.exam.portal.Model.modules;

public interface moduleRepository extends JpaRepository<modules, Integer> {

    Optional<modules> findByModuleId(Integer id);

    

    // List<modules> findAllByModuleId(List<Integer> selectedModule);

}
