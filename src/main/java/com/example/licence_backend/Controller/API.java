package com.example.licence_backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class API {
    @GetMapping("/a")
    public String a() {
        return "a";
    }
}
