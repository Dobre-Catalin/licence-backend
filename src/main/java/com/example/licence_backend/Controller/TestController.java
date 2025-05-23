package com.example.licence_backend.Controller;

import com.example.licence_backend.Model.Mapper.SubmitTestDTO;
import com.example.licence_backend.Model.Test.Question;
import com.example.licence_backend.Model.Test.Test;
import com.example.licence_backend.Model.User.User;
import com.example.licence_backend.Repository.QuestionRepository;
import com.example.licence_backend.Repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test")
public class TestController {
    //repo for test questions
    @Autowired
    QuestionRepository questionRepository;
    //repo for tests
    @Autowired
    TestRepository testRepository;

    //constructor
    public TestController(QuestionRepository testQuestionRepository) {
        this.questionRepository = testQuestionRepository;
    }

    //create a test of N questions
    @GetMapping("/create/{n}")
    public List<Long> createTest(@PathVariable int n) {
        Set<Question> createdTest;
        createdTest = questionRepository.findQuestionsRandom(n);
        List<Long> questionIds = createdTest.stream()
                .map(Question::getId)
                .collect(Collectors.toList());
        return questionIds;
    }

    @PostMapping("/submit")
    public void submitTest(@RequestBody SubmitTestDTO submitTestDTO) {
        // Get the user from the security context
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Create a test
        Test test = new Test();
        test.setUser(user);
        test.setGrade(0.0); // Use double for decimal points
        /// get time and set it to test
        Date date = Date.from(Instant.now());
        test.setStartTime(date);

        Set<Question> questions = new HashSet<>();

        // Set the questions
        for (Long questionId : submitTestDTO.getQuestionIds()) {
            Question question = questionRepository.findById(questionId).orElse(null);
            if (question != null) {
                questions.add(question);

                Set<String> correctAnswers = question.getAnswers(); // correct answers from database
                List<String> userAnswers = submitTestDTO.getResponses().get(questionId.intValue()); // user's submitted answers
                String userAnswersAsString = String.join(",", userAnswers);
                test.addAnswer(questionId, userAnswersAsString);
                if (correctAnswers != null && userAnswers != null) {
                    Set<String> userAnswersSet = new HashSet<>(userAnswers);

                    // Check if there are any wrong answers
                    boolean hasWrongAnswer = !correctAnswers.containsAll(userAnswersSet);

                    if (hasWrongAnswer) {
                        // User submitted an answer that isn't correct -> 0 points
                        continue;
                    } else {
                        if (userAnswersSet.size() == correctAnswers.size()) {
                            // All correct answers given
                            test.setGrade(test.getGrade() + 1.0);
                        } else {
                            // Only some correct answers given -> partial score
                            double partialScore = (double) userAnswersSet.size() / (double) correctAnswers.size();
                            test.setGrade(test.getGrade() + partialScore);
                        }
                    }
                }
            }
            test.setQuestions(questions);
        }

        // Save the test
        testRepository.save(test);
    }

    @GetMapping("/getUserTests/{id}")
    public List<Test> getUserTests(@PathVariable Long id) {
        List<Test> tests = this.testRepository.findTestsByUserIdOrderByStartTimeDesc(id);
        return tests;
    }

    @GetMapping("/getMyTests")
    public List<Test> getMyTests() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Test> tests = this.testRepository.getTestsByUserOrderByStartTime(user);
        return tests;
    }

    @GetMapping("/testDetails/{id}")
    public Test getTestDetails(@PathVariable Long id) {
        return this.testRepository.findTestById(id);
    }
}
