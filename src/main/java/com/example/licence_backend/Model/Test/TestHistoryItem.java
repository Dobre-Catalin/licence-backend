package com.example.licence_backend.Model.Test;

import com.example.licence_backend.Model.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
public class TestHistoryItem {
    @Id
    @GeneratedValue
    private Long id;

    // Many-to-one: A test history item is associated with a single user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many-to-many: A test history item can have multiple test questions, and a question can belong to multiple history items
    @ManyToMany
    @JoinTable(
            name = "test_history_item_question",
            joinColumns = @JoinColumn(name = "test_history_item_id"),
            inverseJoinColumns = @JoinColumn(name = "test_question_id"))
    private Set<TestQuestion> questions;
}