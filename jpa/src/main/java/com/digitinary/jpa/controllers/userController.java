package com.digitinary.jpa.controllers;

import com.digitinary.jpa.entities.taskmanagement.Project;
import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.entities.usermanagement.User;
import com.digitinary.jpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService user_service;

    @Autowired
    public UserController(UserService userService) {
        this.user_service = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return user_service.getUsers();
    }

    @GetMapping(path = "{userId}")
    public User getUser(@PathVariable("userId") Integer userid){
        return user_service.getUser(userid);
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user){
        user_service.addUser(user);
    }

    @DeleteMapping(path = "{userId}")
    public void removeUser(@PathVariable("userId") Integer userid){
        user_service.removeUser(userid);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(
            @PathVariable("userId") Integer userid,
            @RequestBody User user
            ){

        user_service.updateUser(userid, user.getFirstName(), user.getLastName(), user.getEmail());
    }

    @GetMapping(path = "{userId}/tasks")
    public Set<Task> getTasks(@PathVariable("userId") Integer userId){
        return user_service.getTasks(userId);
    }

    @GetMapping(path = "{userId}/project")
    public Project getAssignedProject(@PathVariable("userId") Integer userId){
        return user_service.getAssignedProject(userId);
    }

    @GetMapping(path = "{userId}/project/tasks")
    public Set<Task> getTasksInAssignedProject(@PathVariable("userId") Integer userId){
        return getTasksInAssignedProject(userId);
    }


}
