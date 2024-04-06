package com.example.ForumProject.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String firstName;
    private String fatherName;
    private String LastName;
    private String role;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    public User(
            String username,
            String firstName,
            String fatherName,
            String LastName,
            String password,
            String role) {
        this.username = username;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.LastName = LastName;
        this.password = password;
        this.role = role;
    }
}
