package com.example.libraryManagmentSystem.service;


import com.example.libraryManagmentSystem.entity.book; // Adjust package as per your structure
 import com.example.libraryManagmentSystem.entity.IssueBookDTO;
import com.example.libraryManagmentSystem.repository.bookRepository; // Adjust package as per your structure
import com.example.libraryManagmentSystem.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import com.example.libraryManagmentSystem.repository.IssueBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class bookService {

    @Autowired
    private bookRepository bookRepository;
    

    @Autowired
    private IssueBookRepository issueBookRepository;

    // Fetch all books directly from the book entity
    
    
    // Method to add a new book
    public book addBook(book book) {
        if (bookRepository.existsById(book.getId())) {  // existsBYId uses long as argument so we are wrapping int to long and not Long which is wrapper class
            book existingBook = bookRepository.findById(book.getId()).orElseThrow(() ->
                    new ResourceNotFoundException("Book with ID " + book.getId() + " not found."));
            existingBook.setTotalCopies(existingBook.getTotalCopies() + book.getTotalCopies());
            existingBook.setAvailableCopies(existingBook.getAvailableCopies() + book.getTotalCopies());
            return bookRepository.save(existingBook);
        } else
        {
            book.setAvailableCopies(book.getTotalCopies());
            book.setFine(null);         // Fine will be null initially
            book.setIssueDate(null);     // Issue date null
            book.setDueDate(null); 
            return bookRepository.save(book);
        }
    }

    
    // Method to update an existing book
    public book updateBook(book book) {
        // Check if the book exists before updating
        if (!bookRepository.existsById((Long)book.getId())) {
            throw new RuntimeException("Book not found with ID: " + book.getId());
        }
        return bookRepository.save(book);
    }

    // Method to get all books
    public List<book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Method to search for books by title or author
    public List<book> searchBooks(String query) {
        return bookRepository.findByNameOrAuthorContaining(query);
    }

    @SuppressWarnings("unused")
    public String deleteBook(Long id) {
        // Find the book by ID, throw error if not found
        book bookToDelete = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // Check if the book is already issued to a student
        if (issueBookRepository.existsByBookId(id)) {
            IssueBookDTO issue = issueBookRepository.findByBookId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Issue record not found for the book"));
            throw new IllegalArgumentException("Book is already issued by " + issue.getStudentId() + ", Student ID: " + issue.getStudentId());
        }else{
        // If no one has issued the book, delete it
        bookRepository.deleteById(id);
        return "Book deleted successfully";
        }
    }

    public String returnBook(Long bookId, Long studentId) {
        // Check if the student issued the book
        Optional<IssueBookDTO> issueBook = issueBookRepository.findByBookIdAndStudentId(bookId, studentId);
        if (issueBook.isEmpty()) {
            throw new IllegalArgumentException("No record found for the book issued by the student");
        }
    
        // Increase the available copies of the book
        book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
    
        // Remove the record from issue_books table for this book
        issueBookRepository.delete(issueBook.get());
    
        return "Book returned successfully";
    }
    



    
    

}

