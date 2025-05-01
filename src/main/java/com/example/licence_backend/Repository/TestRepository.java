package com.example.licence_backend.Repository;

import com.example.licence_backend.Model.Test.Test;
import com.example.licence_backend.Model.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    public Test findTestById(Long testId);

    List<Test> getTestsByUserOrderByStartTime(User user);

    @Query("SELECT t FROM Test t WHERE t.user.id = :userId ORDER BY t.startTime DESC")
    List<Test> findTestsByUserIdOrderByStartTimeDesc(@Param("userId") Long userId);

}
