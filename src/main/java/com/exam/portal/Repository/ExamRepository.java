package com.exam.portal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.exam.portal.Model.Exam;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query(value = "SELECT *  FROM exams e WHERE e.creator_id = ?1",nativeQuery = true)
    List<Exam> findByOrganiserId(Integer organiser_id);
}
