package com. example. libraryManagmentSystem. controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com. example. libraryManagmentSystem. service.bookService;
import  com. example. libraryManagmentSystem.entity.book;

import java.util.List;

@RestController
@RequestMapping("/librarian")
public class LibrarianController {

    @Autowired
    private bookService bookService; // Assuming you have a service for business logic

    @PostMapping("/add")
    public ResponseEntity<book> addBook(@RequestBody book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<book> updateBook(@RequestBody book book) {
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @GetMapping("/books")
    public ResponseEntity<List<book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // @PostMapping("/issue/{id}")
    // public ResponseEntity<Void> issueBook(@PathVariable Long id) {
    //     bookService.issueBook(id);
    //     return ResponseEntity.noContent().build();
    // }

    // @PostMapping("/return/{id}")
    // public ResponseEntity<Void> returnBook(@PathVariable Long id) {
    //     bookService.returnBook(id);
    //     return ResponseEntity.noContent().build();
    // }

    // @PostMapping("/request/{id}")
    // public ResponseEntity<Void> requestBook(@PathVariable Long id, @RequestParam String studentUsername) {
    //     bookService.requestBook(id, studentUsername);

    //     return ResponseEntity.noContent().build();
    // }


    // @DeleteMapping("/remove-request/{id}")
    // public ResponseEntity<Void> removeRequest(@PathVariable Long id, @RequestParam String studentUsername) {
    //     bookService.removeRequest(id, studentUsername);
    //     return ResponseEntity.noContent().build();
    // }

    @PostMapping("/notify")
    public ResponseEntity<String> notifyLibrarian(@RequestBody List<String> requestedBy) {
        // Here you can define how you want to notify the librarian
        // For now, we'll just log the requested users
        for (String username : requestedBy) {
            System.out.println("Notification: The book has been requested by: " + username);
        }
        return ResponseEntity.ok("Notifications sent to librarian.");
    }








}

