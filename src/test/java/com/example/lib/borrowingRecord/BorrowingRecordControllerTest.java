package com.example.lib.borrowingRecord;

import com.example.lib.patron.Patron;
import com.example.lib.book.Book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(BorrowingRecordController.class)
public class BorrowingRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    private Book book;
    private Patron patron;

    @BeforeEach
    void setup() {
        book = new Book();
        patron = new Patron();
    }

    @Test
    void borrowBook_ShouldBorrowBook() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;

        doNothing().when(borrowingRecordService).borrowBook(bookId, patronId);

        mockMvc.perform(post("/api/borrow/" + bookId + "/patron/" + patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Book borrowed successfully"));

        verify(borrowingRecordService).borrowBook(bookId, patronId);
    }

    @Test
    void returnBook_ShouldReturnBook() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;

        doNothing().when(borrowingRecordService).returnBook(bookId, patronId);

        mockMvc.perform(put("/api/return/" + bookId + "/patron/" + patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Book returned successfully"));

        verify(borrowingRecordService).returnBook(bookId, patronId);
    }
}
