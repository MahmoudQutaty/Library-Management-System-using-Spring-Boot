package com.example.libraryManagementSystem.services;
import com.example.libraryManagementSystem.models.Patron;
import com.example.libraryManagementSystem.repositories.PatronRepository;
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
public class PatronServiceTest {

    //Mock the patronRepository
    @Mock
    private PatronRepository patronRepository;

    //injects the mocks that are created with the bookRepository
    @InjectMocks
    private PatronService patronService;

    @Test
    public void testFindPatron_existingPatron() {
        Long patronId = 1L;
        Patron mockPatron = new Patron();
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(mockPatron));

        ResponseEntity<Patron> response = patronService.findPatron(patronId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPatron, response.getBody());
    }

    @Test
    public void testFindPatron_nonExistingPatron() {
        Long patronId = 1L;
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        ResponseEntity<Patron> response = patronService.findPatron(patronId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdatePatronData_existingPatron() {
        Long patronId = 1L;
        Patron existingPatron = new Patron();
        Patron updatedPatronData = new Patron();
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(existingPatron));
        when(patronRepository.save(existingPatron)).thenReturn(updatedPatronData);

        ResponseEntity<Patron> response = patronService.updatePatronData(patronId, updatedPatronData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPatronData, response.getBody());
    }

    @Test
    public void testUpdatePatronData_nonExistingPatron() {
        Long patronId = 1L;
        Patron updatedPatronData = new Patron();
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        ResponseEntity<Patron> response = patronService.updatePatronData(patronId, updatedPatronData);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeletePatronById_existingPatron() {
        Long patronId = 1L;
        when(patronRepository.existsById(patronId)).thenReturn(true);

        ResponseEntity<?> response = patronService.deletePatronById(patronId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(patronRepository, times(1)).deleteById(patronId);
    }

    @Test
    public void testDeletePatronById_nonExistingPatron() {
        Long patronId = 1L;
        when(patronRepository.existsById(patronId)).thenReturn(false);

        ResponseEntity<?> response = patronService.deletePatronById(patronId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(patronRepository, never()).deleteById(patronId);
    }
}