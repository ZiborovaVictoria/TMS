package com.example.tms.controller;

import com.example.tms.model.Comment;
import com.example.tms.model.Task;
import com.example.tms.service.CommentService;
import com.example.tms.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Tag(description = "API to manage comments", name = "Comment Resource")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Get comments by task", description = "Provides all comments for a task")
    @GetMapping("/task/{taskId}")
    public List<Comment> getCommentsByTask(@PathVariable Long taskId) {
        Task task = taskService.getTask(taskId);
        return commentService.getCommentsByTask(task);
    }

    @Operation(summary = "Creates a comment", description = "Creates a comment for a task")
    @PostMapping("/")
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @Operation(summary = "Updates a comment", description = "Updates a comment")
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return commentService.updateComment(id, comment);
    }

    @Operation(summary = "Delete comment by id", description = "Delete comment by id")
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}