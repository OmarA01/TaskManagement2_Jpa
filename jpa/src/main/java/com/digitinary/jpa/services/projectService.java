package com.digitinary.jpa.services;

import com.digitinary.jpa.entities.taskmanagement.Project;
import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.entities.usermanagement.User;
import com.digitinary.jpa.exceptions.AlreadyExistsException;
import com.digitinary.jpa.exceptions.NotFoundException;
import com.digitinary.jpa.repositories.projectRepository;
import com.digitinary.jpa.repositories.taskRepository;
import com.digitinary.jpa.repositories.userRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {
    private final projectRepository projectRepo;
    private final userRepository userRepo;
    private final taskRepository taskRpo;

    @Autowired
    public ProjectService(projectRepository projectRepo, taskRepository taskRepo, userRepository userRepo, taskRepository taskRpo) {
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
        this.taskRpo = taskRpo;
    }

    @Transactional
    public List<Project> getProjects(){
        return projectRepo.findAll();
    }

    @Transactional
    public Project getProject(Integer projectId){
        Optional<Project> project = projectRepo.findById(projectId);
        if(project.isEmpty())
            throw new NotFoundException("Project not found");

        return project.get();
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
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new NotFoundException("Project doesn't exist"));

        for (Task task : project.getTasks()) {
            project.removeTask(task.getTitle());
        }
        projectRepo.save(project);
        projectRepo.delete(project);
        System.out.println("Project removed successfully");
    }

    @Transactional
    public void updateProject(Integer projectId, String name){
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new NotFoundException("Project doesn't exist"));

        project.setName(name);
        projectRepo.save(project);

        System.out.println("Project updated successfully");
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
    public List<User> getProjectAssignedUsers(Integer projectId){
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new NotFoundException("Project doesn't exist"));

        Set<User> users = project.getUsers();
        return List.copyOf(users);
    }

    @Transactional
    public void assignUserToProject(Integer projectId, Integer userId){
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setProject(project);
        project.addUser(user);
        userRepo.save(user);
        projectRepo.save(project);
    }

    @Transactional
    public void executeTasks(Integer projectId){
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        TaskProcessor taskProcessor = new TaskProcessor(10, taskRpo);
        taskProcessor.executeTasks(project.getTasks().stream().toList());

    }

}
