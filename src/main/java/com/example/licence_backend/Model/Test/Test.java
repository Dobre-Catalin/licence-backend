package com.example.licence_backend.Model.Test;

import com.example.licence_backend.Model.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Entity
public class Test {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JsonBackReference
    private User user;

    @Getter
    @Setter
    @ElementCollection
    private Set<String> correctAnswers;

    @Getter
    @Setter
    @Nullable
    @ElementCollection
    private Set<String> answers;

    @Getter
    @Setter
    @Nullable
    private Double grade;

    @Getter
    @Setter
    private Integer testLength;

    @Getter
    @Setter
    private Date startTime;

    @Getter
    @Setter
    @ManyToMany
    private Set<Question> questions;

    public Test(Set<Question> questions) {
        this.questions = questions;
    }

    public Test() {

    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }
}