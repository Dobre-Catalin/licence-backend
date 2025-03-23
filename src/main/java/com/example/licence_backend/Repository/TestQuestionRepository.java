package com.example.licence_backend.Repository;

import com.example.licence_backend.Model.Test.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TestQuestionRepository extends JpaRepository<TestQuestion, Long> {
    public TestQuestion findTestQuestionById(Long questionId);
}
