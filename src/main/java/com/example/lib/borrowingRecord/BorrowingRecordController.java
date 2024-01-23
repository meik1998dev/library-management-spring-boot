package com.example.lib.borrowingRecord;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {
    private final BorrowingRecordService borrowService;

    @Autowired
    public BorrowingRecordController(BorrowingRecordService borrowService) {
        this.borrowService = borrowService;
    }

    // Allow a patron to borrow a book
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> borrowBook(@Valid @PathVariable Long bookId,@Valid @PathVariable Long patronId) {
        borrowService.borrowBook(bookId, patronId);
        return ResponseEntity.ok().build();
    }

    // Record the return of a borrowed book by a patron
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> returnBook(@Valid @PathVariable Long bookId,@Valid @PathVariable Long patronId) {
        borrowService.returnBook(bookId, patronId);
        return ResponseEntity.ok().build();
    }
}
