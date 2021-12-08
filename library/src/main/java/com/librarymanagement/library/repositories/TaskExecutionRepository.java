package com.librarymanagement.library.repositories;

import com.librarymanagement.library.entities.Genre;
import com.librarymanagement.library.entities.TaskExecution;
import com.librarymanagement.library.nonPersistedEntities.TaskExecutionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskExecutionRepository extends JpaRepository<TaskExecution,Long> {
    @Query("SELECT te FROM TaskExecution te WHERE te.id = :id")
    TaskExecution getTaskExecution(Long id);
    @Query("SELECT te FROM TaskExecution te WHERE te.taskType = :type AND te.data= :data")
    TaskExecution getTaskExecution(TaskExecutionType type, String data);
}
