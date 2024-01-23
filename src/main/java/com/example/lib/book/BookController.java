package com.example.lib.book;

import com.example.lib.ApiResponse.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Retrieve a list of all books
    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        List<Book> books = bookService.findAllBooks();
        ApiResponse apiResponse = new ApiResponse(true, null , books );
        return ResponseEntity.ok(apiResponse);
    }

    // Retrieve details of a specific book by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBookById(@PathVariable String id) {
        Book book = bookService.findBookById(id);
        ApiResponse apiResponse = new ApiResponse(true, null , book );
        return ResponseEntity.ok(apiResponse);
    }

    // Add a new book to the library
    @PostMapping
    public ResponseEntity<ApiResponse> addBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        ApiResponse apiResponse = new ApiResponse(true, "Book created successfully" , savedBook );
        return ResponseEntity.ok(apiResponse);
    }

    // Update an existing book's information
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateBook(@PathVariable Long id,@RequestBody Map<String, Object> updates) {
        Book updatedBook = bookService.updateBook(id, updates);
        ApiResponse apiResponse = new ApiResponse(true, "Book updated successfully" , updatedBook );
        return ResponseEntity.ok(apiResponse);
    }

    // Remove a book from the library
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        ApiResponse apiResponse = new ApiResponse(true, "Book deleted successfully" );
        return ResponseEntity.ok(apiResponse);
    }
}
