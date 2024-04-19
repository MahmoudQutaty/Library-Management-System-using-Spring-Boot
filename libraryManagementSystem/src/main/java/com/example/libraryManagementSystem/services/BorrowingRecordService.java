package com.example.libraryManagementSystem.services;

import com.example.libraryManagementSystem.models.Book;
import com.example.libraryManagementSystem.models.BorrowingRecord;
import com.example.libraryManagementSystem.models.Patron;
import com.example.libraryManagementSystem.repositories.BookRepository;
import com.example.libraryManagementSystem.repositories.BorrowingRecordRepository;
import com.example.libraryManagementSystem.repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowingRecordService {
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PatronRepository patronRepository;

    @Transactional
    public ResponseEntity<BorrowingRecord> createBorrowingRecord(
            Long bookId,
            Long patronId,
            BorrowingRecord borrowingRecord
    ) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);
            Patron patron = patronRepository.findById(patronId).orElse(null);

            if (book == null || patron == null) {
                return ResponseEntity.notFound().build();
            }

            borrowingRecord.setBook(book);
            borrowingRecord.setPatron(patron);

            BorrowingRecord savedRecord = borrowingRecordRepository.save(borrowingRecord);
            return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
