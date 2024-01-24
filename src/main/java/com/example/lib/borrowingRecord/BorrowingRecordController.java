package com.example.lib.borrowingRecord;

import com.example.lib.apiResponse.ApiResponse;
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
    public ResponseEntity<ApiResponse> borrowBook(@Valid @PathVariable Long bookId,@Valid @PathVariable Long patronId) {
        borrowService.borrowBook(bookId, patronId);
        ApiResponse apiResponse = new ApiResponse(true, "Book borrowed successfully");
        return ResponseEntity.ok(apiResponse);
    }

    // Record the return of a borrowed book by a patron
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse> returnBook(@Valid @PathVariable Long bookId,@Valid @PathVariable Long patronId) {
        borrowService.returnBook(bookId, patronId);
        ApiResponse apiResponse = new ApiResponse(true, "Book returned successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
