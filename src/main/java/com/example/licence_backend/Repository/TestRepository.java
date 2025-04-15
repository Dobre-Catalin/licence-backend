package com.example.licence_backend.Repository;

import com.example.licence_backend.Model.Test.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
    public Test findTestById(Long testId);
}
