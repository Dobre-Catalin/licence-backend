package com.example.licence_backend.Model.Mapper;

import java.util.Set;

public class QuestionDTO {
    private Set<String> answers;

    public QuestionDTO(String pathToImage, Set<String> answers) {
        this.answers = answers;
    }

    public Set<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<String> answers) {
        this.answers = answers;
    }
}
