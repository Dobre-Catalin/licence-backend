package com.example.licence_backend.Model.Test;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    @Nullable
    @ManyToMany
    private Set<Test> tests;
}