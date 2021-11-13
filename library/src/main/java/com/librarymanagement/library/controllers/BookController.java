package com.librarymanagement.library.controllers;

import com.librarymanagement.library.entities.Book;
import com.librarymanagement.library.entities.Genre;
import com.librarymanagement.library.entities.Inspection;
import com.librarymanagement.library.entities.Stock;
import com.librarymanagement.library.repositories.BookRepository;
import com.librarymanagement.library.repositories.GenreRepository;
import com.librarymanagement.library.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("/app")
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    GenreRepository genreRepository;

    @GetMapping(value="/books")
    public List<Inspection> getBooks(){
        return bookRepository.getBookView();
    }

    @PutMapping(value = "/decrease/{id}")
    public ResponseEntity<Stock> deleteBook(@PathVariable(value = "id") Long book_id){
        Stock stock = stockRepository.getStockByBookId(book_id);
        Integer stockNum = stock.getNumbers();
        if(stockNum > 1) {
            stock.setNumbers(stockNum - 1);
        } else {
            stock.setNumbers(0);
        }
        Stock updatedStock = stockRepository.save(stock);

        return ResponseEntity.ok(updatedStock);
    }

//    @PostMapping("/newbook")
//    public Book createBook(@RequestParam(required = false) String title,
//                           @RequestParam(required = false) String author,
//                           @RequestParam(required = false) Integer year,
//                           @RequestParam(required = false) String genre) {
//
//        Long id = bookRepository.getDuplicateBook(title, author, year);
//        Book newBook = new Book();
//        newBook.setGenre(genreRepository.getGenreByType(genre));
//        newBook.setAuthor(author);
//        newBook.setYearPublished(year);
//        newBook.setTitle(title);
//        if(id == null) {
//            stockRepository.save(new Stock(newBook, 1));
//            return bookRepository.save(newBook);
//        } else {
//            Stock stock = stockRepository.getStockByBookId(id);
//            stock.setNumbers(stock.getNumbers() + 1);
//            stockRepository.save(stock);
//            return newBook;
//        }
//
//    }

    @PostMapping("/newbook")
    public Book createBook(@RequestBody Book bookData) {

        String title = bookData.getTitle();
        String author = bookData.getAuthor();
        Integer year = bookData.getYearPublished();
        Long id = bookRepository.getDuplicateBook(title, author, year);

        Book newBook = new Book();
        newBook.setGenre(genreRepository.getGenreByType(bookData.getGenre().getGenreType()));
        newBook.setAuthor(author);
        newBook.setYearPublished(year);
        newBook.setTitle(title);
        if(id == null) {
            stockRepository.save(new Stock(newBook, 1));
            return bookRepository.save(newBook);
        } else {
            Stock stock = stockRepository.getStockByBookId(id);
            stock.setNumbers(stock.getNumbers() + 1);
            stockRepository.save(stock);
            return newBook;
        }

    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Long book_id,
                                           @RequestBody Book bookData) throws Exception {
        Book book = bookRepository.findById(book_id)
                .orElseThrow(() -> new Exception("Book not found for this id :: " + book_id));

        book.setTitle(bookData.getTitle());
        book.setAuthor(bookData.getAuthor());
        book.setYearPublished(bookData.getYearPublished());
        book.setGenre(genreRepository.getGenreByType(bookData.getGenre().getGenreType()));
        final Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok(updatedBook);
    }

}
