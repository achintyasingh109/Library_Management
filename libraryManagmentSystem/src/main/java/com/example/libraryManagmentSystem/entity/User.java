package com.example.libraryManagmentSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Use Long to match with database auto-generated ID

    private Integer studentId; // Assuming you want student ID as integer
    private String studentName;
    private String courseName;
    private String branchName;
    private String semester;

    // Getters and Setters
    // (Use Lombok or manually add them)
}
