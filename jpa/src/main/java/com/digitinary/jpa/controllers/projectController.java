package com.digitinary.jpa.controllers;

import com.digitinary.jpa.entities.taskmanagement.Project;
import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.entities.usermanagement.User;
import com.digitinary.jpa.services.ProjectService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> projects = projectService.getProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping(path = "{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable("projectId") Integer projectId) {
        Project project = projectService.getProject(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registerNewProject(@RequestBody Project project) {
        projectService.addProject(project);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{projectId}")
    public ResponseEntity<Void> removeProject(@PathVariable("projectId") Integer projectId) {
        projectService.removeProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "{projectId}")
    public ResponseEntity<Void> updateProject(@PathVariable("projectId") Integer projectId, @RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        projectService.updateProject(projectId, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "{projectId}/tasks")
    public ResponseEntity<List<Task>> getProjectTasks(@PathVariable Integer projectId) {
        List<Task> tasks = projectService.getProjectTasks(projectId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(path = "{projectId}/users")
    public ResponseEntity<List<User>> getProjectAssignedUsers(@PathVariable("projectId") Integer projectId) {
        List<User> users = projectService.getProjectAssignedUsers(projectId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(path = "{projectId}/users/{userId}")
    public ResponseEntity<Void> assignUserToProject(@PathVariable("projectId") Integer projectId, @PathVariable("userId") Integer userId) {
        projectService.assignUserToProject(projectId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "{projectId}/executeTasks")
    public void executeTasks(@PathVariable("projectId") Integer projectId){
        projectService.executeTasks(projectId);
    }
}
