package com.librarymanagement.library.repositories;

import com.librarymanagement.library.entities.Book;
import com.librarymanagement.library.entities.Inspection;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM book_inspection_view", nativeQuery = true)
    List<Inspection> getBookView();

    @Query(value = "SELECT id FROM book WHERE title = :title AND author = :author AND year_published = :year_published", nativeQuery = true)
    Long getDuplicateBook(String title, String author, Integer year_published);

    @Modifying
    @Query("UPDATE Stock SET numbers = 14 where book.id = :id")
    @Transactional
    Integer DecreaseBookStock(Long id);
}
