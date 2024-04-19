package com.example.libraryManagementSystem.repositories;

import com.example.libraryManagementSystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
