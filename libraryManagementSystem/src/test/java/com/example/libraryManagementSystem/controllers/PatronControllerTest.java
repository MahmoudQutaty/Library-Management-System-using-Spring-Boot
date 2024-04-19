package com.example.libraryManagementSystem.controllers;
import com.example.libraryManagementSystem.models.Patron;
import com.example.libraryManagementSystem.repositories.PatronRepository;
import com.example.libraryManagementSystem.services.PatronService;
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
public class PatronControllerTest {

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    @Test
    public void testGetAllPatrons() {
        List<Patron> mockPatrons = new ArrayList<>();
        // Add mock Patron objects to the list
        when(patronRepository.findAll()).thenReturn(mockPatrons);

        ResponseEntity<List<Patron>> response = patronController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPatrons, response.getBody());
    }

    @Test
    public void testGetPatronById_existingPatron() {
        Long patronId = 1L;
        Patron mockPatron = new Patron();
        when(patronService.findPatron(patronId)).thenReturn(ResponseEntity.ok(mockPatron));

        ResponseEntity<Patron> response = patronController.getPatron(patronId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPatron, response.getBody());
    }

    @Test
    public void testAddPatron() {
        Patron patronToAdd = new Patron();
        when(patronRepository.save(patronToAdd)).thenReturn(patronToAdd);

        ResponseEntity<Patron> response = patronController.addPatron(patronToAdd);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(patronToAdd, response.getBody());
    }

    @Test
    public void testUpdatePatron() {
        Long patronId = 1L;
        Patron updatedPatron = new Patron();
        when(patronService.updatePatronData(patronId, updatedPatron)).thenReturn(ResponseEntity.ok(updatedPatron));

        ResponseEntity<Patron> response = patronController.updatePatron(patronId, updatedPatron);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPatron, response.getBody());
    }

    @Test
    public void testDeletePatron() {
        Long patronId = 1L;
        when(patronService.deletePatronById(patronId)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<?> response = patronController.deletePatron(patronId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}