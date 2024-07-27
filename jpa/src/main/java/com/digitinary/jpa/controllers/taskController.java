package com.digitinary.jpa.controllers;

import com.digitinary.jpa.entities.taskmanagement.Project;
import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.entities.usermanagement.User;
import com.digitinary.jpa.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/tasks")
public class TaskController {

    private final TaskService task_service;

    @Autowired
    public TaskController(TaskService taskService) {
        task_service = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(){
        List<Task> tasks = task_service.getTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(path = "{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable("taskId") Integer taskId){
        Task task = task_service.getTask(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registerNewTask(@RequestBody Task task){
        task_service.addTask(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{taskId}")
    public ResponseEntity<Void> removeTask(@PathVariable("taskId") Integer id){
        task_service.removeTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "{taskId}")
    public ResponseEntity<Void> updateTask(
            @PathVariable("taskId") Integer taskId,
            @RequestBody Task task
    ){
        task_service.updateTask(taskId, task);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "{taskId}/users")
    public ResponseEntity<Set<User>> getUsersOfTask(@PathVariable("taskId") Integer taskId){
        Set<User> users = task_service.getUsersOfTask(taskId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "{taskId}/projects")
    public ResponseEntity<Set<Project>> getProjectsTaskIn(@PathVariable("taskId") Integer taskId){
        Set<Project> projects = task_service.getProjectsTaskIn(taskId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping(path = "{taskId}/users/{userId}")
    public ResponseEntity<Void> addTaskToUser(@PathVariable("taskId") Integer taskId, @PathVariable("userId") Integer userId){
        task_service.addTaskToUser(taskId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{taskId}/users/{userId}")
    public ResponseEntity<Void> removeTaskFromUser(@PathVariable("taskId") Integer taskId, @PathVariable("userId") Integer userId){
        task_service.removeTaskFromUser(taskId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "{taskId}/projects/{projectId}")
    public ResponseEntity<Void> addTaskToProject(@PathVariable("taskId") Integer taskId, @PathVariable("projectId") Integer projectId){
        task_service.addTaskToProject(taskId, projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{taskId}/projects/{projectId}")
    public ResponseEntity<Void> removeTaskFromProject(@PathVariable("taskId") Integer taskId, @PathVariable("projectId") Integer projectId){
        task_service.removeTaskFromProject(taskId, projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
