package com.example.tms;

import com.example.tms.controller.CommentController;
import com.example.tms.model.Comment;
import com.example.tms.model.Task;
import com.example.tms.service.CommentService;
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

public class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @Mock
    private TaskService taskService;

    @Mock
    private Comment comment;

    @Mock
    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCommentsByTask_Success() {
        Long taskId = 1L;
        List<Comment> comments = Arrays.asList(new Comment(), new Comment());
        when(taskService.getTask(taskId)).thenReturn(task);
        when(commentService.getCommentsByTask(task)).thenReturn(comments);

        List<Comment> result = commentController.getCommentsByTask(taskId);

        assertEquals(2, result.size());
    }

    @Test
    void createComment_Success() {
        Comment newComment = new Comment();
        when(commentService.createComment(any(Comment.class))).thenReturn(newComment);

        Comment result = commentController.createComment(newComment);

        assertEquals(newComment, result);
    }

    @Test
    void updateComment_Success() {
        Long commentId = 1L;
        Comment updatedComment = new Comment();
        when(commentService.updateComment(anyLong(), any(Comment.class))).thenReturn(updatedComment);

        Comment result = commentController.updateComment(commentId, updatedComment);

        assertEquals(updatedComment, result);
    }

    @Test
    void deleteComment_Success() {
        Long commentId = 1L;

        commentController.deleteComment(commentId);
    }
}

