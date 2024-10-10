package com.example.libraryManagmentSystem.controller;

import com.example.libraryManagmentSystem.entity.book;
 import com.example.libraryManagmentSystem.service.IssueBookService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import com.example.libraryManagmentSystem.service.bookService;
import com.example.libraryManagmentSystem.service.UserService;
import org.springframework.http.HttpStatus;
import com.example.libraryManagmentSystem.entity.IssueBookDTO;    
import com.example.libraryManagmentSystem.entity.User;    
import com.example.libraryManagmentSystem.entity.ReturnBookDTO;   
import com.example.libraryManagmentSystem.repository.IssueBookRepository;

// import java.util.List;
@RestController
@RequestMapping("/api/books")
public class bookController {
    
    @Autowired
    private bookService bookService;


    @Autowired
    private IssueBookService issueBookService;

    @Autowired
    private UserService userService;

    @Autowired
    private IssueBookRepository issueBookRepository;

    
    @SuppressWarnings("null")
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody book book1) {
        try {
            // Convert DTO to Book entity
            book newBook = new book();
            newBook.setId(book1.getId());
            newBook.setName(book1.getName());
            newBook.setAuthor(book1.getAuthor());
            newBook.setTotalCopies(book1.getTotalCopies());
            newBook.setAvailableCopies(book1.getTotalCopies());
         
            
            // Save the book using the service layer
            book savedBook = bookService.addBook(book1);   // This addBook is of bookService class
            return ResponseEntity.status(HttpStatus.OK).body(savedBook);
        }catch (DataIntegrityViolationException e) {
            // Log the exact exception message for debugging purposes
            e.printStackTrace();
        
            // Return a custom error response to the client
            String errorMessage = "Error: " + e.getRootCause().getMessage();  // Fetching the root cause message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } 
    }

    @PostMapping("/issue")
public ResponseEntity<?> issueBook(@RequestBody IssueBookDTO issueBookDTO) {
    try {
        book issuedBook = issueBookService.issueBook(issueBookDTO);
        return ResponseEntity.status(HttpStatus.OK).body(issuedBook);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}


@GetMapping("/list")
    public ResponseEntity<?> listBooks() {
        try {
            return ResponseEntity.ok(bookService.getAllBooks());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching books: " + e.getMessage());
        }
    }

    // API to delete a book by ID (with conditions for issued books)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            String result = bookService.deleteBook(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/return")
public ResponseEntity<?> returnBook(@RequestBody ReturnBookDTO returnBookDTO) {
    try {
        String result = bookService.returnBook(returnBookDTO.getBookId(), returnBookDTO.getStudentId());
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

@GetMapping("/student/{studentId}")
public ResponseEntity<?> getStudentDetails(@PathVariable Long studentId) {
    try {
        User student = userService.findStudentById(studentId);  // Fetch student details
        Optional<IssueBookDTO> issuedBook = issueBookRepository.findByStudentId(studentId);
        if (issuedBook.isPresent()) {
            
        // Create a response object that includes both student and issued book details
        Map<String, Object> response = new HashMap<>();
        response.put("studentId", student.getStudentId());
        response.put("studentName", student.getStudentName());
        response.put("courseName", student.getCourseName());
        response.put("branchName", student.getBranchName());
        response.put("semester", student.getSemester());
        
        // Include issued book details
        response.put("bookId", issuedBook.get().getBookId());
        response.put("bookName", issuedBook.get().getBookName());
        response.put("issueDate", issuedBook.get().getIssueDate());
        response.put("dueDate", issuedBook.get().getDueDate());
        
        return ResponseEntity.ok(response);
    }
        return ResponseEntity.ok(student);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
    }
}
}
