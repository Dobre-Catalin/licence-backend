package com.example.licence_backend.Model.Test;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Setter
    @Getter
    private String pathToImage;

    @Getter
    @Setter
    @Nullable
    @ElementCollection
    private Set<String> answers;

    @Getter
    @Setter
    @Nullable
    @ElementCollection
    private Set<String> possibleAnswers;

    @Nullable
    @ManyToMany
    private Set<Test> tests;
}