package com.example.libraryManagementSystem.controllers;
import com.example.libraryManagementSystem.models.Book;
import com.example.libraryManagementSystem.repositories.BookRepository;
import com.example.libraryManagementSystem.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    public void testGetAllBooks() {
        List<Book> mockBooks = new ArrayList<>();
        // Add mock Book objects to the list
        when(bookRepository.findAll()).thenReturn(mockBooks);

        ResponseEntity<List<Book>> response = bookController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBooks, response.getBody());
    }

    @Test
    public void testGetBookById_existingBook() {
        Long bookId = 1L;
        Book mockBook = new Book();
        when(bookService.findBook(bookId)).thenReturn(ResponseEntity.ok(mockBook));

        ResponseEntity<Book> response = bookController.getBook(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBook, response.getBody());
    }

    @Test
    public void testAddBook() {
        Book bookToAdd = new Book();
        when(bookRepository.save(bookToAdd)).thenReturn(bookToAdd);

        ResponseEntity<Book> response = bookController.addBook(bookToAdd);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookToAdd, response.getBody());
    }

    @Test
    public void testUpdateBook() {
        Long bookId = 1L;
        Book updatedBook = new Book();
        when(bookService.updateBookData(bookId, updatedBook)).thenReturn(ResponseEntity.ok(updatedBook));

        ResponseEntity<Book> response = bookController.updateBook(bookId, updatedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBook, response.getBody());
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        when(bookService.deleteBookById(bookId)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<?> response = bookController.deleteBook(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
