package com.example.assessment.task;

import com.example.assessment.task.dto.CreateTaskRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(Instant.now());
        return repository.save(task);
    }

    public void deleteTask(Long id) {
        boolean removed = repository.deleteById(id);
        if (!removed) {
            throw new TaskNotFoundException(id);
        }
    }

    /**
     * Returns all tasks that currently have the given status.
     *
     * NOTE: there is a known defect in this method that the candidate
     * may be asked to find and fix during the interview.
     */
    public List<Task> getTasksByStatus(TaskStatus status) {
        return repository.findAll().stream()
                // BUG (planted): this returns tasks that do NOT match the status.
                .filter(task -> task.getStatus() != status)
                .toList();
    }
}
