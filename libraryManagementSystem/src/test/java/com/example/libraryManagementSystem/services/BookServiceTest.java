package com.example.libraryManagementSystem.services;
import com.example.libraryManagementSystem.models.Book;
import com.example.libraryManagementSystem.repositories.BookRepository;
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
public class BookServiceTest {

    //Mock the bookRepository
    @Mock
    private BookRepository bookRepository;

    //injects the mocks that are created with the bookRepository
    @InjectMocks
    private BookService bookService;

    @Test
    public void testFindBook_existingBook() {
        Long bookId = 1L;
        Book mockBook = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        ResponseEntity<Book> response = bookService.findBook(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBook, response.getBody());
    }

    @Test
    public void testFindBook_nonExistingBook() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookService.findBook(bookId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateBookData_existingBook() {
        Long bookId = 1L;
        Book existingBook = new Book();
        Book updatedBookData = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(updatedBookData);

        ResponseEntity<Book> response = bookService.updateBookData(bookId, updatedBookData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBookData, response.getBody());
    }

    @Test
    public void testUpdateBookData_nonExistingBook() {
        Long bookId = 1L;
        Book updatedBookData = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookService.updateBookData(bookId, updatedBookData);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteBookById_existingBook() {
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(true);

        ResponseEntity<?> response = bookService.deleteBookById(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    public void testDeleteBookById_nonExistingBook() {
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(false);

        ResponseEntity<?> response = bookService.deleteBookById(bookId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookRepository, never()).deleteById(bookId);
    }
}
