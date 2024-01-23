package com.example.lib.borrowingRecord;
import java.util.List;

import com.example.lib.book.Book;
import com.example.lib.book.BookRepository;
import com.example.lib.patron.Patron;
import com.example.lib.patron.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

class BorrowingRecordServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private PatronRepository patronRepository;
    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    private Book book;
    private Patron patron;
    private BorrowingRecord record;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        patron = new Patron();
        record = new BorrowingRecord();
    }

    @Test
    void borrowBook_ShouldBorrowBook() {
        Long bookId = 1L;
        Long patronId = 1L;

        given(bookRepository.findById(bookId.toString())).willReturn(Optional.of(book));
        given(patronRepository.findById(patronId.toString())).willReturn(Optional.of(patron));

        borrowingRecordService.borrowBook(bookId, patronId);

        then(borrowingRecordRepository).should().save(any(BorrowingRecord.class));
    }

    @Test
    void returnBook_ShouldReturnBook() {
        Long bookId = 1L;
        Long patronId = 1L;
        record.setBook(book);
        record.setPatron(patron);

        given(bookRepository.findById(bookId.toString())).willReturn(Optional.of(book));
        given(patronRepository.findById(patronId.toString())).willReturn(Optional.of(patron));
        given(borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron)).willReturn(Optional.of(record));

        borrowingRecordService.returnBook(bookId, patronId);

        then(borrowingRecordRepository).should().save(record);
    }
}
