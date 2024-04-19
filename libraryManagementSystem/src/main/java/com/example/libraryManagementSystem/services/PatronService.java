package com.example.libraryManagementSystem.services;

import com.example.libraryManagementSystem.models.Patron;
import com.example.libraryManagementSystem.repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PatronService {
    @Autowired
    private PatronRepository patronRepository;

    // method to check if a patron is existing in the database or not and return the result
    @Cacheable(value = "patron", key = "#id")
    public ResponseEntity<Patron> findPatron(Long id){
        Patron patron = patronRepository.findById(id).orElse(null);
        if(patron != null){
            return ResponseEntity.ok(patron);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // method to update the patron data
    @CacheEvict(value = "patronsCache", key = "#root.methodName")
    public ResponseEntity<Patron> updatePatronData(Long id, Patron newPatron){
        Patron patron = patronRepository.findById(id).orElse(null);
        if(patron != null){
            patron.setName(newPatron.getName());
            patron.setContactInfo(newPatron.getContactInfo());
            Patron patron1 = patronRepository.save(patron);
            return new ResponseEntity<>(patron1, HttpStatus.OK);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    // method to delete a patron
    @CacheEvict(value = "patronsCache", key = "#root.methodName")
    public ResponseEntity<?> deletePatronById(Long id) {
        if (patronRepository.existsById(id)) {
            patronRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
