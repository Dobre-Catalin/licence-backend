package com.example.licence_backend.Controller;

import com.example.licence_backend.Model.Mapper.QuestionDTO;
import com.example.licence_backend.Model.Test.Question;
import com.example.licence_backend.Repository.QuestionRepository;
import com.example.licence_backend.Repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {
    //repo for test questions
    @Autowired
    QuestionRepository questionRepository;
    //repo for tests
    TestRepository testRepository;

    @PostMapping(value = "/create")
    public void createQuestion(
            @RequestPart("image") MultipartFile image,
            @RequestPart("questionDTO") QuestionDTO questionDTO) {

        Question question = new Question();
        String path = "uploads/images/" + image.getOriginalFilename();
        question.setPathToImage(path);
        question.setAnswers(questionDTO.getAnswers());
        question.setPossibleAnswers(questionDTO.getPossibleAnswers());
        questionRepository.save(question);
        // Save the image to the server
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(image.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @GetMapping(value = "/getQuestionImage/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) throws Exception {
        // Build the path relative to the project root
        Question question = questionRepository.findById(Long.parseLong(id)).orElse(null);
        assert question != null;
        String imagePath = question.getPathToImage();
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            return ResponseEntity.notFound().build();
        }
        byte[] imageBytes = java.nio.file.Files.readAllBytes(imageFile.toPath());
        return ResponseEntity
                .ok()
                .header("Content-Type", "image/jpeg")
                .body(imageBytes);
    }
}
