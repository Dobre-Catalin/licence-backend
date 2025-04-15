package com.example.licence_backend.Controller;

import com.example.licence_backend.Model.Mapper.QuestionDTO;
import com.example.licence_backend.Model.Test.Question;
import com.example.licence_backend.Repository.QuestionRepository;
import com.example.licence_backend.Repository.TestRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    //repo for test questions
    QuestionRepository questionRepository;
    //repo for tests
    TestRepository testRepository;

    @PostMapping("/create")
    public void createQuestion(@RequestBody MultipartFile image, @RequestBody QuestionDTO questionDTO) {
        Question question = new Question();
        String path = "uploads/images/" + image.getOriginalFilename();
        question.setPathToImage(path);
        question.setAnswers(questionDTO.getAnswers());
        System.out.println("Creating a question...");
    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable long id) {
        questionRepository.deleteById(id);
    }

    @GetMapping("/get/{id}")
    public Question getQuestion(@PathVariable long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @GetMapping("/getAll")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}
