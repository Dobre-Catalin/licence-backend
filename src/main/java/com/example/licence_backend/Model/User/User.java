package com.example.licence_backend.Model.User;

import com.example.licence_backend.Model.Test.TestHistoryItem;
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

    // One-to-many: Teacher has many students (students are users with role as students)
    @Getter
    @Setter
    @OneToMany(mappedBy = "teacher")
    private List<User> students;

    // Many-to-one: A student has one teacher
    @ManyToOne
    @JoinColumn(name = "teacher_id")  // Renaming column to avoid confusion
    private User teacher;

    // One-to-many: A user can have multiple test history items
    @Getter
    @OneToMany(mappedBy = "user")
    private List<TestHistoryItem> testHistory;

    public boolean isTeacher() {
        return this.role == Role.TEACHER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}