package com.digitinary.jpa.controllers;

import com.digitinary.jpa.entities.usermanagement.User;
import com.digitinary.jpa.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class userController {

    private final userService user_service;

    @Autowired
    public userController(userService userService) {
        this.user_service = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return user_service.getUsers();
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user){
        user_service.addUser(user);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Integer id){
        user_service.deleteUser(id);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(
            @PathVariable("userId") Integer id,
            @RequestBody User user
            ){

        user_service.updateUser(id, user.getFirstName(), user.getLastName(), user.getEmail());
    }

}
