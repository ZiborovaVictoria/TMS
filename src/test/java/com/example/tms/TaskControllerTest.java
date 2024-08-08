package com.example.tmы;

import com.example.tms.controller.TaskController;
import com.example.tms.model.Task;
import com.example.tms.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Mock
    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_Success() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskService.getAllTasks()).thenReturn(tasks);

        List<Task> result = taskController.getAllTasks();

        assertEquals(2, result.size());
    }

    @Test
    void getTask_Success() {
        Long taskId = 1L;
        when(taskService.getTask(taskId)).thenReturn(task);

        Task result = taskController.getTask(taskId);

        assertEquals(task, result);
    }

    @Test
    void getTasksByAuthor_Success() {
        Long authorId = 1L;
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskService.getTasksByAuthor(authorId)).thenReturn(tasks);

        List<Task> result = taskController.getTasksByAuthor(authorId);

        assertEquals(2, result.size());
    }

    @Test
    void getTasksByAssignee_Success() {
        Long assigneeId = 1L;
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskService.getTasksByAssignee(assigneeId)).thenReturn(tasks);

        List<Task> result = taskController.getTasksByAssignee(assigneeId);

        assertEquals(2, result.size());
    }

    @Test
    void createTask_Success() {
        Task newTask = new Task();
        when(taskService.createTask(any(Task.class))).thenReturn(newTask);

        Task result = taskController.createTask(newTask);

        assertEquals(newTask, result);
    }

    @Test
    void updateTask_Success() {
        Long taskId = 1L;
        Task updatedTask = new Task();
        when(taskService.updateTask(anyLong(), any(Task.class))).thenReturn(updatedTask);

        Task result = taskController.updateTask(taskId, updatedTask);

        assertEquals(updatedTask, result);
    }

    @Test
    void deleteTask_Success() {
        Long taskId = 1L;

        taskController.deleteTask(taskId);
    }
}
