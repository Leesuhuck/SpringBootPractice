package org.example;

import org.example.model.TodoResponse;
import org.example.repository.TodoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoServerApplication {

    public static void main(String[] args) {

        System.out.println("Hello");

        SpringApplication.run(TodoServerApplication.class, args);
    }
}
