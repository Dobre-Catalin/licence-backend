package com.example.licence_backend.Controller;

import com.example.licence_backend.Model.Test.Question;
import com.example.licence_backend.Model.Test.Test;
import com.example.licence_backend.Model.User.User;
import com.example.licence_backend.Repository.QuestionRepository;
import com.example.licence_backend.Repository.TestRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test")
public class TestController {
    //repo for test questions
    QuestionRepository questionRepository;
    //repo for tests
    TestRepository testRepository;

    //constructor
    public TestController(QuestionRepository testQuestionRepository) {
        this.questionRepository = testQuestionRepository;
    }

    //create a test of N questions
    @GetMapping("/create/{n}")
    public Test createTest(int n) {
        Set<Question> createdTest;
        createdTest = questionRepository.findQuestionsRandom(n);
        return new Test(createdTest);
    }

    @PostMapping("/submit")
    public void submitTest(Test test) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        test.setUser(user);
        test.setGrade(test.getQuestions().stream().mapToInt(question -> {
            if (question.getAnswers().equals(question.getCorrectAnswers())) {
                return 1;
            } else {
                return 0;
            }
        }).sum());
        testRepository.save(test);
    }
}
