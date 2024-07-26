package com.digitinary.jpa.controllers;

import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.entities.usermanagement.User;
import com.digitinary.jpa.services.taskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/tasks")
public class taskController {

    private final taskService task_service;

    @Autowired
    public taskController(taskService taskService) {
        task_service = taskService;
    }

    @GetMapping
    public List<Task> getTasks(){
        return task_service.getTasks();
    }

    @PostMapping
    public void registerNewTask(@RequestBody Task task){
        task_service.addTask(task);
    }

    @DeleteMapping(path = "{taskId}")
    public void deleteTask(@PathVariable("taskId") Integer id){
        task_service.removeTask(id);
    }

    @PutMapping(path = "{taskId}")
    public void updateTask(
            @PathVariable("taskId") Integer id,
            @RequestBody Task task
    ){

        task_service.updateTask(id, task.getTitle(), task.getDescription(), task.getStatus().toString(), task.getPriority().toString(), task.getDueDate().toString());
    }

}
