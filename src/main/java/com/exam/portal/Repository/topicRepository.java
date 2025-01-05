package com.exam.portal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exam.portal.Model.Topic;

import jakarta.transaction.Transactional;

public interface topicRepository extends JpaRepository<Topic, Integer>{

	@Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM topic_registration WHERE topic_id IN(?1)")
    void deltopic(List<String> topicId);

}
