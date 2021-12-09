package com.librarymanagement.library.entities;

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

    @JoinColumn(name = "book_id")
    Long book_id;

    @JoinColumn(name = "reader_id")
    Long reader_id;

    public Boolean getEquivalentTaskBlocked() {
        return equivalentTaskBlocked;
    }

    public void setEquivalentTaskBlocked(Boolean equivalentTaskBlocked) {
        this.equivalentTaskBlocked = equivalentTaskBlocked;
    }

    @Column(name="equivalent_task_blocked")
    Boolean equivalentTaskBlocked;

    public TaskExecution() {
    }

    public TaskExecution(TaskExecutionType taskType, Long book_id, Long reader_id) {
        this.taskType = taskType;
        this.book_id = book_id;
        this.reader_id = reader_id;
    }
}
