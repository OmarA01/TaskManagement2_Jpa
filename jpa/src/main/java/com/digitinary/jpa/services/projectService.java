package com.digitinary.jpa.services;

import com.digitinary.jpa.repositories.projectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class projectService {
    private final projectRepository projectRepo;

    @Autowired
    public projectService(projectRepository projectRepo) {
        this.projectRepo = projectRepo;
    }


}
