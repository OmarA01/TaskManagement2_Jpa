package com.digitinary.jpa.repositories;

import com.digitinary.jpa.entities.taskmanagement.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface taskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByTitle(String title);

    boolean existsByTitle(String title);

    void deleteByTitle(String title);

}
