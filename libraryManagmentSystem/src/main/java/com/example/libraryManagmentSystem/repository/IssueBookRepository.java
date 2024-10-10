package com.example.libraryManagmentSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.libraryManagmentSystem.entity.IssueBookDTO;
// import com.example.libraryManagmentSystem.entity.ReturnBookDTO;
import java.util.Optional;

public interface IssueBookRepository extends JpaRepository<IssueBookDTO, Long> {
    // Optionally, you can add methods here for custom queries if needed
    boolean existsByBookId(Long bookId);

    // Method to find the issued book by bookId
    Optional<IssueBookDTO> findByBookId(Long bookId);
    Optional<IssueBookDTO> findByStudentId(Long studentId);
    Optional<IssueBookDTO> findByBookIdAndStudentId(Long bookId, Long studentId);
}

