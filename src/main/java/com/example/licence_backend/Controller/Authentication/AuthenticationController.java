package com.example.licence_backend.Controller.Authentication;

import com.example.licence_backend.Model.User.Role;
import lombok.RequiredArgsConstructor;
import com.example.licence_backend.Model.User.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final UserService service;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        var response = service.autheticate(request);
        if(response == null){
            System.out.println("Response is null");
            return ResponseEntity.badRequest().build();
        }
        System.out.println("Response is not null");
        System.out.println("Response: " + response);
        System.out.println("Token: " + response.getToken());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        ///return ResponseEntity.ok(service.register(request));
        var response = service.register(request);
        if(response == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegisterRequest request
    ) {
        var response = service.registerAdmin(request);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registerInstructor")
    public ResponseEntity<AuthenticationResponse> registerManager(
            @RequestBody RegisterRequest request
    ) {
        var response = service.registerInstructor(request);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }
}