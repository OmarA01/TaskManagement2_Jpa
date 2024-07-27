package com.digitinary.jpa.controllers;

import com.digitinary.jpa.repositories.userRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

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

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
