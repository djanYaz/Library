package com.librarymanagement.library.entities;

import com.librarymanagement.library.nonPersistedEntities.TaskExecutionType;

import javax.persistence.*;

@Entity
@Table(name="taskexecution")
public class TaskExecution {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name="task_type")
    @Enumerated(EnumType.ORDINAL)
    private TaskExecutionType taskType;

    @Column(name="data")
    private String data;

    @Column(name="equivalent_task_blocked")
    private Boolean equivalentTaskBlocked;

    public TaskExecution() {
    }

    public TaskExecution(Long id, TaskExecutionType taskType, String data, Boolean equivalentTaskBlocked) {
        this.id = id;
        this.taskType = taskType;
        this.data = data;
        this.equivalentTaskBlocked = equivalentTaskBlocked;
    }
}
