package com.example.lib.borrowingRecord;

import com.example.lib.book.Book;
import com.example.lib.patron.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowingRecordRepository  extends JpaRepository<BorrowingRecord, String> {

    Optional<BorrowingRecord> findByBookAndPatronAndReturnDateIsNull(Book book, Patron patron);

    Optional<BorrowingRecord> findByBookAndReturnDateIsNull(Book book);
}
