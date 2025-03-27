package com.example.licence_backend.Model.Test;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String questionText;

    private String pathToImage;

    @Getter
    @Setter
    @ElementCollection
    private Set<String> answers;

    @ManyToMany
    private Set<Test> tests;
}