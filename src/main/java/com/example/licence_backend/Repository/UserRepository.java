package com.example.licence_backend.Repository;

import com.example.licence_backend.Model.User.Role;
import com.example.licence_backend.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    Optional<List<User>> findByTeacherId(int teacherId);

    List<User> findByUsernameContainingIgnoreCase(String partialUsername);

    List<User> findByUsernameContainingIgnoreCaseAndRole(String partialUsername, Role role);

}
