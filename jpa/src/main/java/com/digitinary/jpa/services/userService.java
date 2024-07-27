package com.digitinary.jpa.services;

import com.digitinary.jpa.entities.taskmanagement.Project;
import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.exceptions.AlreadyExistsException;
import com.digitinary.jpa.exceptions.NotFoundException;
import com.digitinary.jpa.repositories.userRepository;
import com.digitinary.jpa.repositories.taskRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.digitinary.jpa.entities.usermanagement.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final userRepository userRepo;

    @Autowired
    public UserService(userRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Transactional
    public User getUser(Integer userId) {
        return findUserById(userId);
    }

    @Transactional
    public void addUser(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent())
            throw new AlreadyExistsException("User already exists");

        userRepo.save(user);
        System.out.println("User created successfully");
    }

    @Transactional
    public void removeUser(Integer userId) {
        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
            System.out.println("User removed successfully");
        } else {
            throw new NotFoundException("User doesn't exist");
        }
    }

    @Transactional
    public void removeUser(String email) {
        if (userRepo.existsByEmail(email)) {
            userRepo.deleteByEmail(email);
            System.out.println("User removed successfully");
        } else {
            throw new NotFoundException("User doesn't exist");
        }
    }

    @Transactional
    public void updateUser(Integer userId, String firstName, String lastName, String email) {
        User user = findUserById(userId);
        user.updateUser(firstName, lastName, email);
        userRepo.save(user);
    }

    @Transactional
    public Set<Task> getTasks(Integer userId) {
        User user = findUserById(userId);
        return user.getTasks();
    }

    @Transactional
    public Project getAssignedProject(Integer userId) {
        User user = findUserById(userId);
        return user.getProject();
    }

    @Transactional
    public Set<Task> getTasksInAssignedProject(Integer userId) {
        User user = findUserById(userId);
        Set<Task> userTasks = user.getTasks();
        Project project = user.getProject();
        Set<Task> projectTasks = project.getTasks();

        // Find the intersection of userTasks and projectTasks
        return userTasks.stream()
                .filter(projectTasks::contains)
                .collect(Collectors.toSet());
    }

    /**
     * Private helper method to reduce code repetition
     * @return User object if found, throws exception if not
     */
    private User findUserById(Integer userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}

