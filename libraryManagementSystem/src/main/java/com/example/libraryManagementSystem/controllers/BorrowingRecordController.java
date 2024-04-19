package com.example.libraryManagementSystem.controllers;

import com.example.libraryManagementSystem.models.BorrowingRecord;
import com.example.libraryManagementSystem.repositories.BorrowingRecordRepository;
import com.example.libraryManagementSystem.services.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingRecordController {
    @Autowired
    private BorrowingRecordService borrowingRecordService;

    // add a new record to the BorrowRecord table
    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> addRecord(@PathVariable Long bookId,
                                                     @PathVariable Long patronId,
                                                     @RequestBody BorrowingRecord borrowingRecord){
        return borrowingRecordService.createBorrowingRecord(bookId,patronId,borrowingRecord);
    }

}
