package org.nttspringapp.service;

import org.nttspringapp.domain.entity.Task;
import org.nttspringapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> listAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        tasks.forEach(task -> task.setOwner(null));
        return tasks;
    }

    public List<Task> listTasksByOwner(Long ownerId) {
        return taskRepository.findByOwnerId(ownerId);
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public void removeTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
