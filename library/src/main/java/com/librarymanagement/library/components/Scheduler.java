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
import java.util.List;

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
    public void checkForNearlyOverdueBorrowedBooks() throws IOException {
        var found=borrowedBookRepository.getBookBorrowingsWithReturnDateEarlierThan(Instant.now().plusSeconds(3600*24*20));
        handleMany(found,TaskExecutionType.WarnOfApproachingDeadline);

    }
    @Scheduled(fixedRate = 10000)
    public void checkForOverdueBorrowedBooks() throws IOException {
        var found=borrowedBookRepository.getBookBorrowingsWithReturnDateEarlierThan(Instant.now());
        handleMany(found,TaskExecutionType.WarnOfOverdueBook);
    }
    @Scheduled(fixedRate = 10000)
    public void checkForExtremelyOverdueBooks() throws IOException {
        var found=borrowedBookRepository.getBookBorrowingsWithReturnDateEarlierThan(Instant.now().plusSeconds(3600*24*20));
        handleMany(found,TaskExecutionType.WarnOfAdministrativeSanction);
    }
    private void handle(BorrowedBook borrowedBook,TaskExecutionType taskExecutionType) throws IOException {
        var active = taskExecutionRepository.getBlockingTaskExecution(borrowedBook.getBook().getId(), borrowedBook.getReader().getId());
        if(active==null){
            taskExecutionRepository.save(new TaskExecution(TaskExecutionType.WarnOfApproachingDeadline,borrowedBook.getBook().getId(), borrowedBook.getReader().getId()));
            emailService.WarnOfApproachingDeadline(borrowedBook.getBook().getTitle(),borrowedBook.getReader().getEmail(),borrowedBook.getReturnDate());
        }
    }
    private void handleMany(List<BorrowedBook> borrowedBooks, TaskExecutionType taskExecutionType) throws IOException{
        borrowedBooks.forEach(a-> {
            try {
                handle(a,taskExecutionType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
