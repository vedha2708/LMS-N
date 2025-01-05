package com.exam.portal.Repository;

import com.exam.portal.Model.Organiser;
import com.exam.portal.Model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

   
    // @Query(value = "SELECT * FROM user_answer u WHERE u.user_exam_id = ?1 AND u.question_id = ?2", nativeQuery=true)
     @Query("SELECT u FROM UserAnswer u WHERE u.userexam.id = ?1 AND u.questions.id = ?2")
    UserAnswer findByUserQuestion(Long userExamId, Long questionId);
    // UserAnswer findByUserQuestion(Long user_id, Long question_id);

    @Query("SELECT COUNT(u) FROM UserAnswer u WHERE u.userexam.id = ?1 AND u.answer_status = true")
    int findCorrectAnswersCount(Long userExamId);

    @Query("SELECT COUNT(u) FROM UserAnswer u WHERE u.userexam.id = ?1 AND u.answer_status = false")
    int findInCorrectAnswersCount(Long userExamId);
}
