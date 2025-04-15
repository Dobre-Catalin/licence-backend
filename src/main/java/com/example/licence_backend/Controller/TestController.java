package com.example.licence_backend.Controller;

import com.example.licence_backend.Model.Test.Question;
import com.example.licence_backend.Model.Test.Test;
import com.example.licence_backend.Model.User.User;
import com.example.licence_backend.Repository.QuestionRepository;
import com.example.licence_backend.Repository.TestRepository;
import jakarta.activation.FileDataSource;
import jakarta.annotation.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
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
    public List<Long> createTest(int n) {
        Set<Question> createdTest;
        createdTest = questionRepository.findQuestionsRandom(n);
        List<Long> questionIds = createdTest.stream()
                .map(Question::getId)
                .collect(Collectors.toList());
        return questionIds;
    }

    @PostMapping("/submit")
    public void submitTest(List<Long> questionIds, List<Set<String>> answers) {
        //get the user from the security context
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //create a test
        Test test = new Test();
        test.setUser(user);
        //set the questions
        for (Long questionId : questionIds) {
            Question question = questionRepository.findById(questionId).orElse(null);
            if (question != null) {
                test.addQuestion(question);
            }
        }
        //set the answers
        for (int i = 0; i < questionIds.size(); i++) {
            Question question = questionRepository.findById(questionIds.get(i)).orElse(null);
            if (question != null) {
                question.setAnswers(answers.get(i));
            }
        }
        //save the test
        testRepository.save(test);
    }
}
