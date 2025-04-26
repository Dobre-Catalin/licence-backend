package com.example.licence_backend.Model.Mapper;

import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Data
public class QuestionResponseDTO {
    Integer questionId;
    private Set<String> answers;
    private Set<String> possibleAnswers;

    public QuestionResponseDTO(Integer questionId, Set<String> answers, Set<String> possibleAnswers) {
        this.questionId = questionId;
        this.answers = answers;
        this.possibleAnswers = possibleAnswers;
    }

}
