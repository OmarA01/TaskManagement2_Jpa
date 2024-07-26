package com.digitinary.jpa.repositories;

import com.digitinary.jpa.entities.taskmanagement.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface projectRepository extends JpaRepository<Project, Integer> {

    Optional<Project> findByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);

}
