package com.example.licence_backend.Model.Test;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class TestQuestion {
    @Id
    @GeneratedValue
    private Long id;

    private String questionText;

    // Many-to-many: A test question can belong to multiple test history items
    @ManyToMany(mappedBy = "questions")
    private Set<TestHistoryItem> testHistoryItems;
}