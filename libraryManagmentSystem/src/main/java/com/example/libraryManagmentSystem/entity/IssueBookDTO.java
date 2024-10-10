package com.example.libraryManagmentSystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name ="issue_books")

public class IssueBookDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private Long studentId;
    private String bookName;
    private LocalDate issueDate;
    private LocalDate dueDate;
    // Getters and Setters
}

