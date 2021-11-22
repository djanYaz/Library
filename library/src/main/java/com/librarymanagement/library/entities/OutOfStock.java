package com.librarymanagement.library.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="outofstock")
public class OutOfStock {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    public OutOfStock() {
    }

    public OutOfStock(Book book, Reader reader, Date dateCreated) {
        this.book = book;
        this.reader = reader;
        this.dateCreated = dateCreated;
    }

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    Reader reader;

    @Column(name="date_created")
    private Date dateCreated;
}
