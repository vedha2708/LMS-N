package com.exam.portal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exam.portal.Model.Option;
import com.exam.portal.Model.Question;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    
    Option findByQuestionsAndOption(Question question, String option);

}
