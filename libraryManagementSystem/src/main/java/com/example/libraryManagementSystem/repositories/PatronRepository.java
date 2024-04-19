package com.example.libraryManagementSystem.repositories;

import com.example.libraryManagementSystem.models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron,Long> {
}
