package com.example.tms.repository;

import com.example.tms.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAuthorId(Long authorId);
    List<Task> findByAssigneeId(Long assigneeId);
}

