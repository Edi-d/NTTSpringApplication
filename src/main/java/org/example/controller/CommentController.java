package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.domain.entity.Comment;
import org.example.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/tasks/{taskId}/comments")
    public List<Comment> getCommentsByTaskId(@PathVariable Long taskId) {
        return commentService.getCommentsByTaskId(taskId);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return comment != null ? ResponseEntity.ok(comment) : ResponseEntity.notFound().build();
    }

    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<Comment> addCommentToTask(
            @PathVariable Long taskId,
            @RequestBody Comment comment) {
        Comment savedComment = commentService.addCommentToTask(taskId, comment);
        return savedComment != null ?
                ResponseEntity.ok(savedComment) :
                ResponseEntity.notFound().build();
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long commentId,
            @RequestBody String content) {
        Comment updatedComment = commentService.updateComment(commentId, content);
        return updatedComment != null ?
                ResponseEntity.ok(updatedComment) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}