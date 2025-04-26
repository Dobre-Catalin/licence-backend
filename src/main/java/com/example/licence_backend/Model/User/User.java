package com.example.licence_backend.Model.User;

import com.example.licence_backend.Model.Test.Test;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Integer id;

    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    @Getter
    private String firstname;
    @Setter
    @Getter
    private String lastname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    @OneToMany(mappedBy = "teacher")
    @JsonManagedReference
    private List<User> students;

    @ManyToOne
    @JsonBackReference
    private User teacher;

    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Test> testHistory;

    @Getter
    @Setter
    boolean isActive;

    public boolean isTeacher() {
        return this.role == Role.TEACHER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public void addStudent(User student) {
        this.students.add(student);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}