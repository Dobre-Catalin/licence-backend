package com.example.licence_backend.Repository;

import com.example.licence_backend.Model.Test.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    Optional<Answer> findById(int id);
}
