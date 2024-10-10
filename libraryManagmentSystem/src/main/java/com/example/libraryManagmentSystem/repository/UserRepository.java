package com.example.libraryManagmentSystem.repository;



import java.util.Optional;
import com.example.libraryManagmentSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByStudentId(Integer studentId);
    Optional<User> findByStudentId(Long studentId);
}



