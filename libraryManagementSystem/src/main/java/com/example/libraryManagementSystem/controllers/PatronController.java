package com.example.libraryManagementSystem.controllers;

import com.example.libraryManagementSystem.models.Patron;
import com.example.libraryManagementSystem.repositories.PatronRepository;
import com.example.libraryManagementSystem.services.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private PatronService patronService;

    // get all the patrons from database
    @GetMapping()
    @Cacheable(value = "patronsCache", key = "#root.methodName")
    public ResponseEntity<List<Patron>> getAll(){
        List<Patron> patrons = patronRepository.findAll();
        return new ResponseEntity<>(patrons, HttpStatus.OK);
    }

    //get a specific patron by id
    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatron(@PathVariable Long id){
        return patronService.findPatron(id);
    }

    // Add a new patron to the table in the database
    @PostMapping
    @CacheEvict(value = {"patronsCache","patron"}, key = "#root.methodName")
    public ResponseEntity<Patron> addPatron(@RequestBody Patron patron){
        Patron addedPatron = patronRepository.save(patron);
        return new ResponseEntity<>(addedPatron,HttpStatus.CREATED);
    }

    //Update patron data
    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patron){
        return patronService.updatePatronData(id,patron);
    }

    // Delete a patron record from the patron table
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatron(@PathVariable Long id){
        return patronService.deletePatronById(id);
    }
}
