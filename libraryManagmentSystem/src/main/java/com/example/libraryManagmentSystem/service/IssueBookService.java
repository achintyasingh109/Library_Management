package com.example.libraryManagmentSystem.service;
import com.example.libraryManagmentSystem.entity.User;
import com.example.libraryManagmentSystem.entity.IssueBookDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.libraryManagmentSystem.repository.bookRepository;
import com.example.libraryManagmentSystem.repository.IssueBookRepository;
import com.example.libraryManagmentSystem.repository.UserRepository;
import com.example.libraryManagmentSystem.entity.book;
import java.util.Optional;


@Service
public class IssueBookService {

    @Autowired
    private bookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueBookRepository issueBookRepository;

    @SuppressWarnings("unused")
    @Transactional
    public book issueBook(IssueBookDTO issueBookDTO) {
        // Validate book existence and check availability
        book book = bookRepository.findById(issueBookDTO.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        if (!book.getName().equalsIgnoreCase(issueBookDTO.getBookName())) {
            throw new IllegalArgumentException("Book name or ID is incorrect");
        }

        if (book.getAvailableCopies() <= 0) {
            throw new IllegalArgumentException("Copies not available");
        }

        // Validate student existence
        User student = userRepository.findById(issueBookDTO.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));

        // Validate due date
        if (issueBookDTO.getDueDate().isBefore(issueBookDTO.getIssueDate())) {
            throw new IllegalArgumentException("Due date cannot be before the issue date");
        }
        Optional<IssueBookDTO> existingIssue = issueBookRepository.findByBookIdAndStudentId(
            issueBookDTO.getBookId(), issueBookDTO.getStudentId());
        if (existingIssue.isPresent()) {
            throw new IllegalArgumentException("Student has already issued this book");
        }

        // Decrement available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // Create and save the issued book record in a separate table
        IssueBookDTO issueBook = new IssueBookDTO();
        issueBook.setId(issueBookDTO.getId());
        issueBook.setBookId(issueBookDTO.getBookId());
        issueBook.setStudentId(issueBookDTO.getStudentId());
        issueBook.setBookName(issueBookDTO.getBookName());
        issueBook.setIssueDate(issueBookDTO.getIssueDate());
        issueBook.setDueDate(issueBookDTO.getDueDate());

        // Save the issued book record to the "issue_books" table
        issueBookRepository.save(issueBook);

        return book;
    }
}

