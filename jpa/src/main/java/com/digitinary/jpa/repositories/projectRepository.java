package com.digitinary.jpa.repositories;

import com.digitinary.jpa.entities.taskmanagement.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface projectRepository extends JpaRepository<Project, Integer> {
}
