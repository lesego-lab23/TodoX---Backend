package com.todo.todo.controller;

import com.todo.todo.dto.TaskRequest;
import com.todo.todo.entity.Task;
import com.todo.todo.repository.TaskRepository;
import com.todo.todo.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create-task")
    public Task createTask(
            @RequestBody TaskRequest request,
            @RequestHeader("X-USER-EMAIL") String email
    ) {
        return taskService.createTask(request, email);
    }

    // READ ALL
    @GetMapping
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }


    // UPDATE
    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Integer id,
            @RequestBody Task task
    ) {
        return taskService.updateTask(id, task);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }

}
