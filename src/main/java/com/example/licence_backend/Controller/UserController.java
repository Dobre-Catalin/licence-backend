package com.example.licence_backend.Controller;

import com.example.licence_backend.Controller.Authentication.UserService;
import com.example.licence_backend.Model.User.Role;
import com.example.licence_backend.Model.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/studentsByTeacher/{username}")
    public ResponseEntity<List<User>> getAllStudentsByTeacher(
            @PathVariable("username") String teacherId
    ){
        User teacher = service.getUserByUsername(teacherId);
        return ResponseEntity.ok(service.getAllStudentsByTeacher(teacherId));
    }

    @GetMapping("/{role}/{name}")
    public ResponseEntity<List<User>> getAllUsersByRoleAndName(
            @PathVariable("role") Role role,
            @PathVariable("name") String name
    ){
        return ResponseEntity.ok(service.getAllStudentsByName(name, role));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<User> editUser(
            @PathVariable("id") Integer id,
            @RequestBody User user
    ){
        service.editUser(user, id);
        return ResponseEntity.ok(user);
    }

    //delete user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") Integer id
    ){
        service.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addStudent/{studentId}/to/{teacherId}")
    public ResponseEntity<Void> addStudentToTeacher(
            @PathVariable("studentId") String studentId,
            @PathVariable("teacherId") String teacherId
    ){
        service.addStudentToTeacher(studentId, teacherId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
