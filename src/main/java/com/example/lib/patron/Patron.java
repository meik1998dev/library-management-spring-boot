package com.example.lib.patron;


import com.example.lib.borrowingRecord.BorrowingRecord;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "patrons")
public class Patron {
    @Id
    private String id;
    private String name;
    private String contactInformation;

    public Patron(String id, String name, String contactInformation) {
        this.id = id;
        this.name = name;
        this.contactInformation = contactInformation;
    }

    @OneToMany(mappedBy = "patron")
    private List<BorrowingRecord> borrowingRecords;

    public Patron() {

    }
}
