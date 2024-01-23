package com.example.lib.patron;

import com.example.lib.exceptions.NoResourceFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.BDDMockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private PatronValidator patronValidator;

    @InjectMocks
    private PatronService patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllPatrons_ShouldReturnListOfPatrons() {
        List<Patron> patrons = Arrays.asList(new Patron(), new Patron());
        given(patronRepository.findAll()).willReturn(patrons);

        List<Patron> result = patronService.findAllPatrons();

        assertNotNull(result);
        assertEquals(patrons.size(), result.size());
    }

    @Test
    void findPatronById_WithExistingId_ShouldReturnPatron() {
        Long id = 1L;
        Patron patron = new Patron();
        given(patronRepository.findById(id.toString())).willReturn(Optional.of(patron));

        Patron result = patronService.findPatronById(id);

        assertNotNull(result);
    }

    @Test
    void findPatronById_WithNonExistingId_ShouldThrowException() {
        Long id = 1L;
        given(patronRepository.findById(id.toString())).willReturn(Optional.empty());

        assertThrows(NoResourceFoundException.class, () -> patronService.findPatronById(id));
    }

    @Test
    void addPatron_ShouldSavePatron() {
        Patron patron = new Patron();
        given(patronRepository.save(patron)).willReturn(patron);

        Patron result = patronService.addPatron(patron);

        assertNotNull(result);
    }

    @Test
    void updatePatron_WithValidUpdates_ShouldUpdatePatron() {
        Long id = 1L;
        Patron patron = new Patron();
        given(patronRepository.findById(id.toString())).willReturn(Optional.of(patron));
        willDoNothing().given(patronValidator).validateUpdates(any(Map.class));
        given(patronRepository.save(any(Patron.class))).willReturn(patron);

        Map<String, Object> updates = Map.of("name", "New Name");
        Patron result = patronService.updatePatron(id, updates);

        assertNotNull(result);
        verify(patronRepository).save(patron);
    }

    @Test
    void deletePatron_WithExistingId_ShouldDelete() {
        Long id = 1L;
        given(patronRepository.existsById(id.toString())).willReturn(true);
        willDoNothing().given(patronRepository).deleteById(id.toString());

        patronService.deletePatron(id);

        verify(patronRepository).deleteById(id.toString());
    }

    @Test
    void deletePatron_WithNonExistingId_ShouldThrowException() {
        Long id = 1L;
        given(patronRepository.existsById(id.toString())).willReturn(false);

        assertThrows(NoResourceFoundException.class, () -> patronService.deletePatron(id));
    }
}
