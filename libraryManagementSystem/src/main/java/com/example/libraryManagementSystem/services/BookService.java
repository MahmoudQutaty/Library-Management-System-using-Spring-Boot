package com.example.libraryManagementSystem.services;

import com.example.libraryManagementSystem.models.Book;
import com.example.libraryManagementSystem.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    // method to check if a book is existing in the database or not and return the result

    @Cacheable(value = "book", key = "#id")
    public ResponseEntity<Book> findBook(Long id){
        Book book = bookRepository.findById(id).orElse(null);
        if(book != null){
            return ResponseEntity.ok(book);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // method to update the book data
    @CacheEvict(value = "booksCache", key = "#root.methodName")
    public ResponseEntity<Book> updateBookData(Long id, Book newBook){
        Book book = bookRepository.findById(id).orElse(null);
        if(book != null){
            book.setTitle(newBook.getTitle());
            book.setAuthor(newBook.getAuthor());
            book.setPublicationYear(newBook.getPublicationYear());
            book.setIsbn(newBook.getIsbn());
            Book book1 = bookRepository.save(book);
            return new ResponseEntity<>(book1, HttpStatus.OK);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    // method to delete a book
    @CacheEvict(value = "booksCache", key = "#root.methodName")
    public ResponseEntity<?> deleteBookById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
