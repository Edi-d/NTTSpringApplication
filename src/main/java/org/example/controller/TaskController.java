package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.domain.entity.Task;
import org.example.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> listAllTasks() {
        return taskService.listAllTasks();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{taskId}/effort")
    public ResponseEntity<Task> updateRemainingEffort(@PathVariable Long taskId, @RequestParam int effort) {
        Task updatedTask = taskService.updateRemainingEffort(taskId, effort);
        return updatedTask != null ? ResponseEntity.ok(updatedTask) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> removeTaskById(@PathVariable Long taskId) {
        taskService.removeTaskById(taskId);
        return ResponseEntity.noContent().build();
    }
}

