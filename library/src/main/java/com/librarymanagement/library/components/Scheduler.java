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
        var found=borrowedBookRepository.getBookBorrowingsWithReturnDateEarlierThan(Instant.now().minusSeconds(3600*24*20));
        handleMany(found,TaskExecutionType.WarnOfAdministrativeSanction);
    }
    private void handle(BorrowedBook borrowedBook,TaskExecutionType taskExecutionType) throws IOException {
        var active = taskExecutionRepository.getBlockingTaskExecution(borrowedBook.getBook().getId(), borrowedBook.getReader().getId());
        if(active==null){
            taskExecutionRepository.save(new TaskExecution(taskExecutionType,borrowedBook.getBook().getId(), borrowedBook.getReader().getId()));
            executeWarning(taskExecutionType,borrowedBook);
        }else if(active!=null&&active.getTaskType().ordinal()<taskExecutionType.ordinal()){
            active.setEquivalentTaskBlocked(false);
            taskExecutionRepository.save(new TaskExecution(taskExecutionType,borrowedBook.getBook().getId(), borrowedBook.getReader().getId()));
            taskExecutionRepository.save(active);
            executeWarning(taskExecutionType,borrowedBook);
        }
    }
    private void executeWarning(TaskExecutionType taskExecutionType,BorrowedBook borrowedBook) throws IOException{
        switch(taskExecutionType){
            case WarnOfApproachingDeadline:
                emailService.WarnOfApproachingDeadline(borrowedBook.getReader().getEmail(),borrowedBook.getBook().getTitle(),borrowedBook.getReturnDate());
                break;
            case WarnOfAdministrativeSanction:
                emailService.WarnOfAdministrativeSanction(borrowedBook.getReader().getEmail(),borrowedBook.getBook().getTitle(),borrowedBook.getReturnDate());
                break;
            case WarnOfOverdueBook:
                emailService.WarnOfOverdueBook(borrowedBook.getReader().getEmail(),borrowedBook.getBook().getTitle(),borrowedBook.getReturnDate());
                break;
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
