package com.librarymanagement.library.repositories;

import com.librarymanagement.library.entities.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    @Query("SELECT COUNT(b.reader.id) FROM BorrowedBook b WHERE b.reader.id = :reader_id")
    Long getReaderCountBooks(Long reader_id);
}
