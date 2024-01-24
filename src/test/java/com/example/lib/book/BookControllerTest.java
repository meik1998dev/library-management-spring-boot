package com.example.lib.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void getAllBooks_ShouldReturnBooks() throws Exception {
        List<Book> books = Collections.singletonList(new Book());
        given(bookService.findAllBooks()).willReturn(books);

        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0]").exists());
    }

    @Test
    void getBookById_ShouldReturnBook() throws Exception {
        Book book = new Book(); // Assume this is a valid book object
        given(bookService.findBookById("1")).willReturn(book);

        mockMvc.perform(get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void addBook_ShouldCreateBook() throws Exception {
        Book book = new Book();
        book.setTitle("Example Book Title");
        book.setAuthor("Author Name");
        book.setISBN("1234567890");
        book.setPublicationYear(1899);

        Book savedBook = new Book();

        given(bookService.addBook(any(Book.class))).willReturn(savedBook);

        ObjectMapper objectMapper = new ObjectMapper();
        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Book created successfully"))
                .andExpect(jsonPath("$.data").isNotEmpty()); // Additional checks as needed
    }

    @Test
    void updateBook_ShouldUpdateBook() throws Exception {
        Long bookId = 1L;
        Book updatedBook = new Book(); // Assume this is the updated book object
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "Updated Book Title");

        given(bookService.updateBook(eq(bookId), any(Map.class))).willReturn(updatedBook);

        ObjectMapper objectMapper = new ObjectMapper();
        String updatesJson = objectMapper.writeValueAsString(updates);

        mockMvc.perform(put("/api/books/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatesJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Book updated successfully"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void deleteBook_ShouldDeleteBook() throws Exception {
        String bookId = "1";

        // Assuming deleteBook returns true when the book is successfully deleted
        given(bookService.deleteBook(bookId)).willReturn(true);

        mockMvc.perform(delete("/api/books/" + bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Book deleted successfully"));

        verify(bookService).deleteBook(bookId);
    }

}
