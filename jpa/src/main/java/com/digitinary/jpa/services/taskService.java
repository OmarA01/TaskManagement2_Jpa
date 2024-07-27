package com.digitinary.jpa.services;

import com.digitinary.jpa.entities.taskmanagement.Project;
import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.entities.usermanagement.User;
import com.digitinary.jpa.enums.Priority;
import com.digitinary.jpa.enums.Status;
import com.digitinary.jpa.exceptions.AlreadyExistsException;
import com.digitinary.jpa.exceptions.NotFoundException;
import com.digitinary.jpa.repositories.taskRepository;
import com.digitinary.jpa.repositories.userRepository;
import com.digitinary.jpa.repositories.projectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {
    private final taskRepository taskRepo;
    private final userRepository userRepo;
    private final projectRepository projectRepo;

    @Autowired
    public TaskService(taskRepository taskRepo, userRepository userRepo, projectRepository projectRepo){
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
    }

    @Transactional
    public List<Task> getTasks(){
        return taskRepo.findAll();
    }

    @Transactional
    public Task getTask(Integer taskId){
        Optional<Task> task = taskRepo.findById(taskId);
        if(task.isEmpty())
            throw new NotFoundException("Task not found");

        return task.get();
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
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));

        for (User user : task.getUsers()) {
            user.removeTask(task.getTitle());
            userRepo.save(user);
        }

        for (Project project : task.getProjects()) {
            project.removeTask(task.getTitle());
            projectRepo.save(project);
        }

        taskRepo.delete(task);
        System.out.println("Task removed successfully");
    }

    @Transactional
    public void updateTask(Integer taskId, Task task) {
        Task myTask= taskRepo.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        myTask.updateTask(task);
        taskRepo.save(myTask);
    }

    @Transactional
    public Set<User> getUsersOfTask(Integer taskId){
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        return task.getUsers();
    }

    @Transactional
    public Set<Project> getProjectsTaskIn(Integer taskId){
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        return task.getProjects();
    }

    @Transactional
    public void addTaskToUser(Integer taskId, Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        user.addTask(task);
        userRepo.save(user);
    }

    @Transactional
    public void removeTaskFromUser(Integer taskId, Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        user.removeTask(task.getTitle());
        userRepo.save(user);
    }

    @Transactional
    public void addTaskToProject(Integer taskId, Integer projectId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        project.addTask(task);
        projectRepo.save(project);
    }

    @Transactional
    public void removeTaskFromProject(Integer taskId, Integer projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        project.removeTask(task.getTitle());
        projectRepo.save(project);
    }


}
