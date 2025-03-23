package com.example.licence_backend.Repository;

import com.example.licence_backend.Model.Test.TestHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestHistoryItemRepository extends JpaRepository<TestHistoryItem, Long> {
    public TestHistoryItem findTestHistoryItemById(Long id);
    public TestHistoryItem findTestHistoryItemBy(String username);
}
