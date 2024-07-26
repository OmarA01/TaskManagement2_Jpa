package com.digitinary.jpa.controllers;

import com.digitinary.jpa.entities.taskmanagement.Project;
import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.services.projectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/projects")
public class projectController {
    private final projectService project_service;

    @Autowired
    public projectController(projectService projectService) {
        project_service = projectService;
    }

    @GetMapping
    public List<Project> getProjects(){
        return project_service.getProjects();
    }

    @PostMapping
    public void registerNewProject(@RequestBody Project project){
        project_service.addProject(project);
    }

    @DeleteMapping(path = "{projectId}")
    public void deleteProject(@PathVariable("projectId") Integer id){
        project_service.removeProject(id);
    }

    @GetMapping(path = "{projectId}/tasks")
    public List<Task> getProjectTasks(@PathVariable Integer projectId){
        return project_service.getProjectTasks(projectId);
    }

    @PostMapping("{projectId}/tasks/{taskId}")
    public void addTaskToProject(@PathVariable Integer projectId, @PathVariable Integer taskId) {
        project_service.addTaskToProject(projectId, taskId);
    }

    @DeleteMapping("{projectId}/tasks/{taskId}")
    public void removeTaskFromProject(@PathVariable Integer projectId, @PathVariable Integer taskId) {
        project_service.removeTaskFromProject(projectId, taskId);
    }

}
