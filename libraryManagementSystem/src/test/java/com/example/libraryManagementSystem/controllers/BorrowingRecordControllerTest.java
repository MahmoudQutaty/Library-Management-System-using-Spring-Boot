package com.example.libraryManagementSystem.controllers;
import com.example.libraryManagementSystem.models.BorrowingRecord;
import com.example.libraryManagementSystem.services.BorrowingRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordControllerTest {

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    @Test
    public void testAddRecord() {
        Long bookId = 1L;
        Long patronId = 2L;
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        ResponseEntity<BorrowingRecord> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(borrowingRecord);

        // Mocking the service method
        when(borrowingRecordService.createBorrowingRecord(bookId, patronId, borrowingRecord)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<BorrowingRecord> actualResponse = borrowingRecordController.addRecord(bookId, patronId, borrowingRecord);

        // Verifying the result
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        // Verify that the service method was called with the correct arguments
        verify(borrowingRecordService).createBorrowingRecord(bookId, patronId, borrowingRecord);
    }
}
