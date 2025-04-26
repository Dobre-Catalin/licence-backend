package com.example.licence_backend.Repository;

import com.example.licence_backend.Model.Test.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    public Question findQuestionById(Long questionId);

    @Query(value = "SELECT * FROM question ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    public Set<Question> findQuestionsRandom(@Param("limit") int limit);
}
