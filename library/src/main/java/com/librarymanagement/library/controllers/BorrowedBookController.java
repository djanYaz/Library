package com.librarymanagement.library.controllers;

import com.librarymanagement.library.entities.*;
import com.librarymanagement.library.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("/borrows")
public class BorrowedBookController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowedBookRepository borrowedBookRepository;

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    OutOfStockRepository outOfStockRepository;

    @GetMapping("all")
    public List<BorrowedBook> getAllBorrows(){
        return borrowedBookRepository.findAll();
    }

    @PostMapping("givebook")
    public String GiveBook(@RequestParam(required = false, name = "reader_id") Long reader_id,
                           @RequestParam(required = false, name = "book_id") Long book_id) throws Exception {
        Date today = new Date();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now().plusDays(30);
        Date returnDate = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        Reader reader = readerRepository.findById(reader_id).orElseThrow(() -> new Exception("Reader not found for this id :: " + reader_id));
        Book book = bookRepository.findById(book_id).orElseThrow(() -> new Exception("Book not found for this id :: " + book_id));

        Integer bookStock = book.getStock().getNumbers();
        int readerGottenBooks = reader.getBorrowedBooks().size();
        if(readerGottenBooks < 5 && bookStock != 0){
            BorrowedBook borrowedBook = new BorrowedBook(book, reader, today, returnDate);
            borrowedBookRepository.save(borrowedBook);
            Stock stock = book.getStock();
            stock.setNumbers(stock.getNumbers()-1);
            stockRepository.save(stock);
            return "Успешна заявка!";
        } else if(bookStock == 0) {
            OutOfStock outOfStock = new OutOfStock(book, reader, today);
            outOfStockRepository.save(outOfStock);
            return "Книгата " + book.getTitle() + " не е налична";
        } else if(readerGottenBooks == 5 || readerGottenBooks > 5) {
            return "Вече сте взели допустимото количество книги";
        }
        return "Не е успешно!";
    }
}
