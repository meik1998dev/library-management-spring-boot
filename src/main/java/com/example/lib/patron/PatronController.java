package com.example.lib.patron;

import com.example.lib.ApiResponse.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    // Retrieve a list of all patrons
    @GetMapping
    public ResponseEntity<ApiResponse> getAllPatrons() {
        List<Patron> patrons = patronService.findAllPatrons();
        ApiResponse apiResponse = new ApiResponse(true, null , patrons );
        return ResponseEntity.ok(apiResponse);
    }

    // Retrieve details of a specific patron by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPatronById(@PathVariable Long id) {
        Patron patron = patronService.findPatronById(id);
        ApiResponse apiResponse = new ApiResponse(true, null , patron );
        return ResponseEntity.ok(apiResponse);
    }

    // Add a new patron to the system
    @PostMapping
    public ResponseEntity<ApiResponse> addPatron(@Valid @RequestBody Patron patron) {
        Patron savedPatron = patronService.addPatron(patron);
        ApiResponse apiResponse = new ApiResponse(true, "New patron added successfully" , savedPatron );
        return ResponseEntity.ok(apiResponse);
    }

    // Update an existing patron's information
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePatron(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Patron updatedPatron = patronService.updatePatron(id, updates);
        ApiResponse apiResponse = new ApiResponse(true, "patron updated successfully" , updatedPatron );
        return ResponseEntity.ok(apiResponse);
    }

    // Remove a patron from the system
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        ApiResponse apiResponse = new ApiResponse(true, "patron deleted successfully"  );
        return ResponseEntity.ok(apiResponse);
    }
}
