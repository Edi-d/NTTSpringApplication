package org.example.service;

import lombok.AllArgsConstructor;
import org.example.domain.entity.Task;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public void removeTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task updateRemainingEffort(Long taskId, int effort) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            task.setRemainingEffort(effort);
            return taskRepository.save(task);
        }
        return null;
    }

    public List<Task> findByOwnerId(Long ownerId) {
        return taskRepository.findByOwnerId(ownerId);
    }
}

