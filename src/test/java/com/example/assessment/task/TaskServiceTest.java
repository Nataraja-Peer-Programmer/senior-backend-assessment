package com.example.assessment.task;

import com.example.assessment.task.dto.CreateTaskRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link TaskService} using the real in-memory repository.
 *
 * These baseline tests pass out of the box. During the interview the
 * candidate may be asked to add a test for {@code getTasksByStatus}, which
 * currently contains a planted defect (see SOLUTION.md).
 */
class TaskServiceTest {

    private TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService(new TaskRepository());
    }

    @Test
    void createTask_assignsIdAndDefaultsToTodo() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Write docs");
        request.setDescription("Document the API");

        Task created = service.createTask(request);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getTitle()).isEqualTo("Write docs");
        assertThat(created.getStatus()).isEqualTo(TaskStatus.TODO);
        assertThat(created.getCreatedAt()).isNotNull();
    }

    @Test
    void getTaskById_whenMissing_throwsNotFound() {
        assertThatThrownBy(() -> service.getTaskById(999L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void getAllTasks_returnsCreatedTasks() {
        service.createTask(newRequest("A"));
        service.createTask(newRequest("B"));

        List<Task> all = service.getAllTasks();

        assertThat(all).hasSize(2);
    }

    @Test
    void deleteTask_whenMissing_throwsNotFound() {
        assertThatThrownBy(() -> service.deleteTask(42L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    private CreateTaskRequest newRequest(String title) {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle(title);
        return request;
    }
}
