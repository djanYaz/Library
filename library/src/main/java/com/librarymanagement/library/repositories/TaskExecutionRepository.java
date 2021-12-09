package com.librarymanagement.library.repositories;

import com.librarymanagement.library.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskExecutionRepository extends JpaRepository<TaskExecution,Long> {
    @Query("SELECT te FROM TaskExecution te WHERE te.id = :id")
    TaskExecution getTaskExecution(Long id);
    @Query("SELECT te FROM TaskExecution te WHERE te.book_id= :book_id AND te.reader_id=:reader_id AND te.equivalentTaskBlocked=true")
    TaskExecution getBlockingTaskExecution(Long book_id, Long reader_id);
}
