package com.example.lib.borrowingRecord;

import com.example.lib.book.Book;
import com.example.lib.book.BookRepository;
import com.example.lib.exceptions.NoResourceFoundException;
import com.example.lib.patron.Patron;
import com.example.lib.patron.PatronRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowingRecordService {
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    public BorrowingRecordService(BookRepository bookRepository, PatronRepository patronRepository , BorrowingRecordRepository borrowingRecordRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    @Transactional
    public void borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId.toString())
                .orElseThrow(() -> new NoResourceFoundException("Book not found with id: " + bookId));

        Patron patron = patronRepository.findById(patronId.toString())
                .orElseThrow(() -> new NoResourceFoundException("Patron not found with id: " + patronId));

        if (!isBookAvailableForBorrow(book)) {
            throw new NoResourceFoundException("Book is not available for borrowing.");
        }

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowingDate(LocalDate.now());

        borrowingRecordRepository.save(record);
    }

    @Transactional
    public void returnBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId.toString())
                .orElseThrow(() -> new NoResourceFoundException("Book not found with id: " + bookId));

        Patron patron = patronRepository.findById(patronId.toString())
                .orElseThrow(() -> new NoResourceFoundException("Patron not found with id: " + patronId));

        BorrowingRecord record = borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron)
                .orElseThrow(() -> new NoResourceFoundException("No borrowing record found for this book and patron."));

        record.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(record);
    }

    private boolean isBookAvailableForBorrow(Book book) {
       // check if there are any active borrowing records without a return date
        return borrowingRecordRepository.findByBookAndReturnDateIsNull(book).isEmpty();
    }
}
