package com.example.tms.service;

import com.example.tms.model.Comment;
import com.example.tms.model.Task;
import com.example.tms.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentsByTask(Task task) {
        return commentRepository.findByTask(task);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, Comment comment) {
        Comment existingComment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        existingComment.setContent(comment.getContent());
        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
