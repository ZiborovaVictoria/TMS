package com.example.tms.service;

import com.example.tms.model.Task;
import com.example.tms.model.User;
import com.example.tms.repository.TaskRepository;
import com.example.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public List<Task> getTasksByAuthor(Long authorId) {
        return taskRepository.findByAuthorId(authorId);
    }

    public List<Task> getTasksByAssignee(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId);
    }

    public Task createTask(Task task) {

        User author = userRepository.findById(Math.toIntExact(task.getAuthor().getId()))
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + task.getAuthor().getId()));
        User assignee = userRepository.findById(Math.toIntExact(task.getAssignee().getId()))
                .orElseThrow(() -> new RuntimeException("Assignee not found with id: " + task.getAssignee().getId()));

        task.setAuthor(author);
        task.setAssignee(assignee);

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }

        User author = userRepository.findById(Math.toIntExact(task.getAuthor().getId()))
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + task.getAuthor().getId()));
        User assignee = userRepository.findById(Math.toIntExact(task.getAssignee().getId()))
                .orElseThrow(() -> new RuntimeException("Assignee not found with id: " + task.getAssignee().getId()));

        task.setId(id);
        task.setAuthor(author);
        task.setAssignee(assignee);

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }
}
