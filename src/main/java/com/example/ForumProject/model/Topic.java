package com.example.ForumProject.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY) // множество топиков к одному пользователю
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    public Topic(String title) {
        this.title = title;
    }

}
