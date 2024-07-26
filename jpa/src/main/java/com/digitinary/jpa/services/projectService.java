package com.digitinary.jpa.services;

import com.digitinary.jpa.entities.taskmanagement.Project;
import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.exceptions.AlreadyExistsException;
import com.digitinary.jpa.exceptions.NotFoundException;
import com.digitinary.jpa.repositories.projectRepository;
import com.digitinary.jpa.repositories.taskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class projectService {
    private final projectRepository projectRepo;
    private final taskRepository taskRepo;


    @Autowired
    public projectService(projectRepository projectRepo, taskRepository taskRepo) {
        this.projectRepo = projectRepo;
        this.taskRepo = taskRepo;
    }

    @Transactional
    public List<Project> getProjects(){
        return projectRepo.findAll();
    }

    @Transactional
    public List<Task> getProjectTasks(Integer projectId){
        Optional<Project> project = projectRepo.findById(projectId);
        if (project.isPresent()) {
            return List.copyOf(project.get().getTasks());
        } else {
            throw new NotFoundException("Project not found");
        }
    }

    @Transactional
    public void addProject(Project project){
        if (projectRepo.findByName(project.getName()).isEmpty()) {
            projectRepo.save(project);
            System.out.println("Project created successfully");
        } else {
            throw new AlreadyExistsException("Project already exists");
        }
    }

    @Transactional
    public void removeProject(Integer projectId) {
        if (projectRepo.existsById(projectId)) {
            projectRepo.deleteById(projectId);
            System.out.println("Porject removed successfully");
        } else {
            throw new NotFoundException("Project doesn't exist");
        }
    }

    @Transactional
    public void removeProject(String name) {
        if (projectRepo.existsByName(name)) {
            projectRepo.deleteByName(name);
            System.out.println("Project removed successfully");
        } else {
            throw new NotFoundException("Project doesn't exist");
        }
    }

    @Transactional
    public void addTaskToProject(Integer projectId, Integer taskId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        project.addTask(task);
        projectRepo.save(project);
    }

    @Transactional
    public void removeTaskFromProject(Integer projectId, Integer taskId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        project.removeTask(task.getTitle());
        projectRepo.save(project);
    }

}
