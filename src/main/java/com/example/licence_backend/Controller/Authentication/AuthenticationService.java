package com.example.licence_backend.Controller.Authentication;

import com.example.licence_backend.Config.JwtService;
import lombok.RequiredArgsConstructor;
import com.example.licence_backend.Config.JwtAuthenticationFilter;
import com.example.licence_backend.Model.User.Role;
import com.example.licence_backend.Model.User.User;
import com.example.licence_backend.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private String username;

    public AuthenticationResponse autheticate(AuthenticationRequest request) {
        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            System.out.println("Password does not match");
            return null;
        }
        var jwtToken = jwtService.generateToken(user);
        System.out.println("JWT Token: " + jwtToken);
        System.out.println("Role: " + user.getRole());
        this.username = user.getUsername();
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().toString())
                .build();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .isActive(true)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
        .build();
    }

    public String getUsernameRole(String username) {
        User user = repository.findByUsername(username).orElseThrow();
        return user.getRole().toString();
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void deleteUser(Integer id) {
        User user = repository.findById(id).orElseThrow();
        repository.delete(user);
    }

    public void editUser(User usernew, Integer id){
        User user = repository.findById(id).orElseThrow();
        user.setFirstname(usernew.getFirstname());
        user.setLastname(usernew.getLastname());
        user.setUsername(usernew.getUsername());
        System.out.println("Password: " + usernew.getPassword());
        //if password is not "" then encode it
        if(!usernew.getPassword().equals("")){
            user.setPassword(passwordEncoder.encode(usernew.getPassword()));
        }
        ///else keep the old password
        user.setRole(usernew.getRole());

        repository.save(user);
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerInstructor(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.TEACHER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public List<User> getAllStudentsByName(String name, Role role) {
        List<User> users = repository.findByUsernameContainingIgnoreCaseAndRole(name, role);
        return users;
    }

    public List<User> getAllStudentsByTeacher(String teacherId) {
        User teacher = repository.findByUsername(teacherId).orElseThrow();
        List<User> students = new ArrayList<>();
        for (User student : teacher.getStudents()) {
            if (student.getRole() == Role.STUDENT) {
                students.add(student);
            }
        }
        return students;
    }

    public User getUserByUsername(String teacherId) {
        User user = repository.findByUsername(teacherId).orElseThrow();
        return user;
    }

    public void addStudentToTeacher(String studentId, String teacherId) {
        User teacher = repository.findByUsername(teacherId).orElseThrow();
        User student = repository.findByUsername(studentId).orElseThrow();
        student.setTeacher(teacher);
        repository.save(student);
        teacher.addStudent(student);
        repository.save(teacher);
    }
}



/*
@Service
public class AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthenticationResponse authenticateUser(String username, String password) {
        boolean exists = userRepository.existsByUsernameAndPassword(username, password);
        if (exists) {
            String token = generateToken(username);
            return new AuthenticationResponse(true, token);
        } else {
            return new AuthenticationResponse(false, null);
        }
    }

    private String generateToken(String username) {
        Date expirationDate = new Date(System.currentTimeMillis() + 3600000);
        var user = userRepository.findByUsername(username);
        int userId = user.get().getId();
        return "{" +
                "\"id\": \"" + userId + "\"," +
                "\"expirationDate\": \"" + expirationDate + "\"" +
                "}";
    }
}
*/