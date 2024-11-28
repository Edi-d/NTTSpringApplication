package org.nttspringapp.controller;

import org.nttspringapp.domain.entity.Task;
import org.nttspringapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> listAllTasks() {
        return taskService.listAllTasks();
    }

    @GetMapping("/owner/{ownerId}")
    public List<Task> listTasksByOwner(@PathVariable Long ownerId) {
        return taskService.listTasksByOwner(ownerId);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    // Update remaining effort
//    @PutMapping("/{taskId}/effort")
//    public ResponseEntity<Task> updateRemainingEffort(@PathVariable Long taskId, @RequestParam int effort) {
//        Task updatedTask = taskService.updateRemainingEffort(taskId, effort);
//        return updatedTask != null ? ResponseEntity.ok(updatedTask) : ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> removeTaskById(@PathVariable Long taskId) {
        taskService.removeTaskById(taskId);
        return ResponseEntity.noContent().build();
    }
}
