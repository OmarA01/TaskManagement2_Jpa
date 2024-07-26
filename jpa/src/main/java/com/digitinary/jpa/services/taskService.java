package com.digitinary.jpa.services;

import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.repositories.taskRepository;
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

    public List<Task> getTasks(){
        return taskRepo.findAll();
    }

}
