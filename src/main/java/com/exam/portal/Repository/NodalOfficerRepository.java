package com.exam.portal.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exam.portal.Model.NodalOfficer;

import jakarta.transaction.Transactional;

public interface NodalOfficerRepository extends JpaRepository<NodalOfficer, Integer> {

    NodalOfficer findByEmail(String email);

    // @Modifying
    // @Transactional
    // @Query(nativeQuery = true, value = "DELETE FROM nodal_officer WHERE id IN(?1)")
    // void deleteNodal(List<Integer> Id);

   

}
