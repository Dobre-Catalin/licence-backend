package com.example.licence_backend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class Misc {
    @GetMapping("/a")
    public String a() {
        return "a";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) throws IOException {
        // Build the path relative to the project root
        String projectRoot = System.getProperty("user.dir");
        String imagePath = projectRoot + File.separator + "uploads" + File.separator + "images" + File.separator + id + ".jpg";
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

    @PostMapping("/addImage")
    public ResponseEntity<?> addImage(@RequestParam("image") MultipartFile image) throws IOException {
        String projectRoot = System.getProperty("user.dir");
        String relativePath = "uploads/images/";
        File uploadDir = new File(projectRoot, relativePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String fullPath = uploadDir.getAbsolutePath() + File.separator + image.getOriginalFilename();
        image.transferTo(new File(fullPath));
        return ResponseEntity.ok().body("Image added");
    }

}
