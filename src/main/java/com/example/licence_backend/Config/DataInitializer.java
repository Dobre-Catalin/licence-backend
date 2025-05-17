package com.example.licence_backend.Config;

import com.example.licence_backend.Controller.Authentication.AuthenticationController;
import com.example.licence_backend.Controller.Authentication.RegisterRequest;
import com.example.licence_backend.Controller.Authentication.UserService;
import com.example.licence_backend.Model.Test.Answer;
import com.example.licence_backend.Repository.AnswerRepository;
import com.example.licence_backend.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final UserService authenticationService;

    public DataInitializer(AnswerRepository answerRepository, UserRepository userRepository, AuthenticationController authenticationController, UserService authenticationService) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public void run(String... args) {
        if (answerRepository.count() == 0) {
            answerRepository.save(new Answer("Car"));
            answerRepository.save(new Answer("Pedestrian"));
            answerRepository.save(new Answer("Bike"));
            answerRepository.save(new Answer("Car"));
        }
        if (userRepository.count() == 0) {
            authenticationService.registerAdmin(new RegisterRequest("admin@admin.com", "admin", "admin", "admin", "admin"));
        }
    }
}
