package com.example.libraryManagmentSystem.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//to modify and simplify code we are gonnna use lombok

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "books")
public class book {

    @Id
    
    private Long id;

    private String name;
    private String author;
    private int totalCopies;
    private int availableCopies;

    private String issuedTo = null;

    private LocalDate issueDate = null;
    private LocalDate dueDate = null;

    private Double fine = null;
    // Method to get the list of requests
    @Getter
    @ElementCollection
    private List<String> requestedBy = new ArrayList<>();

    // Constructors, Getters, Setters
//    public book() {}

    public book(Long id,String name, String author, int totalCopies) {
        this.name = name;
        this.author = author;
        this.totalCopies = totalCopies;
       // this.availableCopies = availableCopies;
        this.id=id;
    }

    public void addRequest(String studentUsername) {
        if (!requestedBy.contains(studentUsername)) {
            requestedBy.add(studentUsername);
        }
    }

    // Method to remove a request
    public void removeRequest(String studentUsername) {
        requestedBy.remove(studentUsername);
    }

    public void clearRequests() {
        requestedBy.clear();
    }

// Now We Have Used Lombok Annotation So We Dont Need To Add Getter And Setter Method.

//    public List<String> getRequestedBy() {
//        return requestedBy;
//    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public int getTotalCopies() {
//        return totalCopies;
//    }
//
//    public void setTotalCopies(int totalCopies) {
//        this.totalCopies = totalCopies;
//    }
//
//    public int getAvailableCopies() {
//        return availableCopies;
//    }
//
//    public void setAvailableCopies(int availableCopies) {
//        this.availableCopies = availableCopies;
//    }
//
//    public LocalDate getIssueDate() {
//        return issueDate;
//    }
//
//    public void setIssueDate(LocalDate issueDate) {
//        this.issueDate = issueDate;
//    }
//
//    public LocalDate getDueDate() {
//        return dueDate;
//    }
//
//    public void setDueDate(LocalDate dueDate) {
//        this.dueDate = dueDate;
//    }
//
//    public double getFine() {
//        return fine;
//    }
//
//    public void setFine(double fine) {
//        this.fine = fine;
//    }



}
