package com.example.libraryManagementSystem.repositories;

import com.example.libraryManagementSystem.models.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Long> {
}
