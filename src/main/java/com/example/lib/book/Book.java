package com.example.lib.book;

import com.example.lib.borrowingRecord.BorrowingRecord;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  String id;
    private  String title;
    private  String author;
    private  int publicationYear;
    private  String ISBN;

    public Book(String id, String title, String author, int publicationYear, String ISBN) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
    }


    @OneToMany(mappedBy = "book")
    private List<BorrowingRecord> borrowingRecords;

    public Book() {

    }

    public void setId(String id) {
        this.id = id;
    }
}