package com.digitinary.jpa.services;

import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.entities.usermanagement.User;
import com.digitinary.jpa.exceptions.AlreadyExistsException;
import com.digitinary.jpa.exceptions.NotFoundException;
import com.digitinary.jpa.repositories.taskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class taskService {
    private final taskRepository taskRepo;

    @Autowired
    public taskService(taskRepository taskRepo){
        this.taskRepo = taskRepo;
    }

    @Transactional
    public List<Task> getTasks(){
        return taskRepo.findAll();
    }

    @Transactional
    public void addTask(Task task){
        if (taskRepo.findByTitle(task.getTitle()).isEmpty()) {
            taskRepo.save(task);
            System.out.println("Task created successfully");
        } else {
            throw new AlreadyExistsException("Task already exists");
        }
    }

    @Transactional
    public void removeTask(Integer taskId) {
        if (taskRepo.existsById(taskId)) {
            taskRepo.deleteById(taskId);
            System.out.println("Task removed successfully");
        } else {
            throw new NotFoundException("Task doesn't exist");
        }
    }

    @Transactional
    public void removeTask(String title) {
        if (taskRepo.existsByTitle(title)) {
            taskRepo.deleteByTitle(title);
            System.out.println("Task removed successfully");
        } else {
            throw new NotFoundException("Task doesn't exist");
        }
    }

    @Transactional
    public void updateTask(Integer taskId, String title, String description, String status, String priority, String dueDate) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        task.updateTask(title, description, status, priority, dueDate);
        taskRepo.save(task);
    }



}
