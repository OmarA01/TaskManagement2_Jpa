package com.digitinary.jpa.controllers;

import com.digitinary.jpa.entities.usermanagement.User;
import com.digitinary.jpa.repositories.userRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class userConfig {

    @Bean
    CommandLineRunner commandLineRunner(userRepository userRepo){
        return args -> {
            /*User Ahmad = new User(
                   "Ahmad",
                   "Saber",
                   "Ahmad@gmail.com"
            );

            userRepo.saveAll(List.of(Ahmad));*/

            //userRepo.deleteAll();
        };

    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
}
