package com.example.tms.controller;

import com.example.tms.model.Task;
import com.example.tms.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(description = "Api to manage tasks",
        name = "Task Resource")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Get all tasks",
            description = "Provides all tasks")
    @GetMapping("/")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Get task by id",
            description = "Provides a task by id")
    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @Operation(summary = "Get task by author",
            description = "Provides a list of tasks by author")
    @GetMapping("/author/{authorId}")
    public List<Task> getTasksByAuthor(@PathVariable Long authorId) {
        return taskService.getTasksByAuthor(authorId);
    }

    @Operation(summary = "Get task by assignee",
            description = "Provides a list of tasks by assignee")
    @GetMapping("/assignee/{assigneeId}")
    public List<Task> getTasksByAssignee(@PathVariable Long assigneeId) {
        return taskService.getTasksByAssignee(assigneeId);
    }

    @Operation(summary = "Creates a task",
            description = "Creates a task")
    @PostMapping("/")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @Operation(summary = "Updates task",
            description = "Updates task")
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @Operation(summary = "Delete task by id",
            description = "Delete task by id")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }


}

