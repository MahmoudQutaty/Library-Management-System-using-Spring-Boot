package com.example.libraryManagementSystem.controllers;

import com.example.libraryManagementSystem.models.Book;
import com.example.libraryManagementSystem.repositories.BookRepository;
import com.example.libraryManagementSystem.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    // get all the books from database
    @GetMapping()
    @Cacheable(value = "booksCache", key = "#root.methodName")
    public ResponseEntity<List<Book>> getAll(){
        List<Book> books = bookRepository.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    //get a specific book by id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id){
        return bookService.findBook(id);
    }

    // Add a new book to the table in the database
    @PostMapping
    @CacheEvict(value = {"booksCache","book"}, key = "#root.methodName")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book addedBook = bookRepository.save(book);
        return new ResponseEntity<>(addedBook,HttpStatus.CREATED);
    }

    //Update book data
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){
        return bookService.updateBookData(id,book);
    }

    // Delete a book record from the book table
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        return bookService.deleteBookById(id);
    }
}
