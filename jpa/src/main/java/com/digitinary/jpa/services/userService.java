package com.digitinary.jpa.services;

import com.digitinary.jpa.exceptions.AlreadyExistsException;
import com.digitinary.jpa.exceptions.NotFoundException;
import com.digitinary.jpa.repositories.userRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.digitinary.jpa.entities.usermanagement.User;

import java.util.List;

@Service
public class userService {

    private final userRepository userRepo;

    @Autowired
    public userService(userRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @Transactional
    public void addUser(User user) {
        if (userRepo.findByEmail(user.getEmail()).isEmpty()) {
            userRepo.save(user);
            System.out.println("User created successfully");
        } else {
            throw new AlreadyExistsException("User already exists");
        }
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
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.updateUser(firstName, lastName, email);
        userRepo.save(user);
    }

    @Transactional
    public void deleteUser(Integer userId){
        if(!userRepo.existsById(userId))
            throw new NotFoundException("User doesn't exist");

        userRepo.deleteById(userId);
    }
}
