package com.todo.todo.service;

import com.todo.todo.dto.TaskRequest;
import com.todo.todo.entity.Task;
import com.todo.todo.entity.User;
import com.todo.todo.repository.TaskRepository;
import com.todo.todo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(TaskRequest request, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task taskEntity = new Task();
        taskEntity.setTask(request.getTask());
        taskEntity.setCompleted(false);
        taskEntity.setUser(user);

        var task = taskRepository.save(taskEntity);

        return task;
    }

    //Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get task by id
    public Task getTaskById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // Update task
    public Task updateTask(Integer id, Task updatedTask) {
        Task existingTask = getTaskById(id);

        existingTask.setTask(updatedTask.getTask());
        existingTask.setCompleted(updatedTask.isCompleted());

        return taskRepository.save(existingTask);
    }



    // Delete task
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}
