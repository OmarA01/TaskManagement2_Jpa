package com.digitinary.jpa.repositories;

import com.digitinary.jpa.entities.taskmanagement.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface taskRepository extends JpaRepository<Task, Integer> {
}
