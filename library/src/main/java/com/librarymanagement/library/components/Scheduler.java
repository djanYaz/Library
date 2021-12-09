package com.librarymanagement.library.components;

import com.librarymanagement.library.entities.BorrowedBook;
import com.librarymanagement.library.entities.TaskExecution;
import com.librarymanagement.library.entities.TaskExecutionType;
import com.librarymanagement.library.repositories.BookRepository;
import com.librarymanagement.library.repositories.BorrowedBookRepository;
import com.librarymanagement.library.repositories.TaskExecutionRepository;
import com.librarymanagement.library.services.emailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class Scheduler {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BorrowedBookRepository borrowedBookRepository;
    @Autowired
    TaskExecutionRepository taskExecutionRepository;
    @Autowired
    EmailService emailService;
    @Scheduled(fixedRate = 10000)
    public void checkForNearlyOverdueBorrowedBooks(){
        var nearlyOverdueBorrowedBooks=borrowedBookRepository.getBookBorrowingsWithReturnDateEarlierThan(Instant.now().minusSeconds(3600*24));
        bookRepository.findDistinctGenreTypes().forEach(System.out::println);
    }
    @Scheduled(fixedRate = 10000)
    public void checkForOverdueBorrowedBooks(){
        var nearlyOverdueBorrowedBooks=borrowedBookRepository.getBookBorrowingsWithReturnDateEarlierThan(Instant.now());
        bookRepository.findDistinctGenreTypes().forEach(System.out::println);
    }
    @Scheduled(fixedRate = 10000)
    public void checkForExtremelyOverdueBooks(){
        var nearlyOverdueBorrowedBooks=borrowedBookRepository.getBookBorrowingsWithReturnDateEarlierThan(Instant.now().plusSeconds(3600*24*20));
        bookRepository.findDistinctGenreTypes().forEach(System.out::println);
    }
    private void nearlyOverdueHandling(BorrowedBook borrowedBook) throws IOException {
        var active = taskExecutionRepository.getBlockingTaskExecution(borrowedBook.getBook().getId(), borrowedBook.getReader().getId());
        if(active==null){
            taskExecutionRepository.save(new TaskExecution(TaskExecutionType.WarnOfApproachingDeadline,borrowedBook.getBook().getId(), borrowedBook.getReader().getId()));
            emailService.WarnOfApproachingDeadline(borrowedBook.getBook().getTitle(),borrowedBook.getReader().getEmail(),borrowedBook.getReturnDate());
        }
    }

    private void overdueHandling(BorrowedBook borrowedBook) throws IOException {
        var active = taskExecutionRepository.getBlockingTaskExecution(borrowedBook.getBook().getId(), borrowedBook.getReader().getId());
        if(active==null){
            taskExecutionRepository.save(new TaskExecution(TaskExecutionType.WarnOfOverdueBook,borrowedBook.getBook().getId(), borrowedBook.getReader().getId()));
            emailService.WarnOfOverdueBook(borrowedBook.getBook().getTitle(),borrowedBook.getReader().getEmail(),borrowedBook.getReturnDate());
        }
    }

    private void extremelyOverdueHandling(BorrowedBook borrowedBook) throws IOException {
        var active = taskExecutionRepository.getBlockingTaskExecution(borrowedBook.getBook().getId(), borrowedBook.getReader().getId());
        if(active==null){
            taskExecutionRepository.save(new TaskExecution(TaskExecutionType.WarnOfAdministrativeSanction,borrowedBook.getBook().getId(), borrowedBook.getReader().getId()));
            emailService.WarnOfAdministrativeSanction(borrowedBook.getBook().getTitle(),borrowedBook.getReader().getEmail(),borrowedBook.getReturnDate());
        }
    }
}
