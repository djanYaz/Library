package com.librarymanagement.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="borrowedbook")
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    public BorrowedBook() {
    }

    public BorrowedBook(Book book, Reader reader, Date borrowDate, Date returnDate) {
        this.book = book;
        this.reader = reader;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reader_id")
    Reader reader;

    @Column(name="borrow_date")
    private Date borrowDate;

    @Column(name="return_date")
    private Date returnDate;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
