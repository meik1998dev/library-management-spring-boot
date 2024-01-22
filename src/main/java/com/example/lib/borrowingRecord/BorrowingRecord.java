package com.example.lib.borrowingRecord;

import com.example.lib.book.Book;
import com.example.lib.patron.Patron;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;

    private LocalDate borrowingDate;
    private LocalDate returnDate;
}
