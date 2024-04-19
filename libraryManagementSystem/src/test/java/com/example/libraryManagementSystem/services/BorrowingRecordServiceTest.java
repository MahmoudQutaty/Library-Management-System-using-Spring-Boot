package com.example.libraryManagementSystem.services;
import com.example.libraryManagementSystem.models.Book;
import com.example.libraryManagementSystem.models.BorrowingRecord;
import com.example.libraryManagementSystem.models.Patron;
import com.example.libraryManagementSystem.repositories.BookRepository;
import com.example.libraryManagementSystem.repositories.BorrowingRecordRepository;
import com.example.libraryManagementSystem.repositories.PatronRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    @Test
    public void testCreateBorrowingRecord_successful() {
        Long bookId = 1L;
        Long patronId = 1L;
        Book mockBook = new Book();
        Patron mockPatron = new Patron();
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(mockPatron));
        when(borrowingRecordRepository.save(borrowingRecord)).thenReturn(borrowingRecord);

        ResponseEntity<BorrowingRecord> response = borrowingRecordService.createBorrowingRecord(bookId, patronId, borrowingRecord);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(borrowingRecord, response.getBody());
        assertEquals(mockBook, borrowingRecord.getBook());
        assertEquals(mockPatron, borrowingRecord.getPatron());
    }

    @Test
    public void testCreateBorrowingRecord_bookNotFound() {
        Long bookId = 1L;
        Long patronId = 1L;
        Patron mockPatron = new Patron();
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(mockPatron));

        ResponseEntity<BorrowingRecord> response = borrowingRecordService.createBorrowingRecord(bookId, patronId, borrowingRecord);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateBorrowingRecord_patronNotFound() {
        Long bookId = 1L;
        Long patronId = 1L;
        Book mockBook = new Book();
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        ResponseEntity<BorrowingRecord> response = borrowingRecordService.createBorrowingRecord(bookId, patronId, borrowingRecord);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateBorrowingRecord_exceptionThrown() {
        Long bookId = 1L;
        Long patronId = 1L;
        Book mockBook = new Book();
        Patron mockPatron = new Patron();
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(mockPatron));
        when(borrowingRecordRepository.save(borrowingRecord)).thenThrow(new RuntimeException("Failed to save"));

        ResponseEntity<BorrowingRecord> response = borrowingRecordService.createBorrowingRecord(bookId, patronId, borrowingRecord);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}