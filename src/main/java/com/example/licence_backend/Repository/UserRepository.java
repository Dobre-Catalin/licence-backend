package com.example.licence_backend.Repository;

import com.example.licence_backend.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);
}
