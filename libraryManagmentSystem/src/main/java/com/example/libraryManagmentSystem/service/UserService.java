package com.example.libraryManagmentSystem.service;
import java.util.Optional;


import com.example.libraryManagmentSystem.entity.User;
import com.example.libraryManagmentSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (userRepository.existsByStudentId(user.getStudentId())) {
            throw new IllegalArgumentException("Student ID already exists."); // Throw an exception if ID exists
        }
        return userRepository.save(user);
    }

    public User findStudentById(Long studentId) {
        Optional<User> userOptional = userRepository.findByStudentId(studentId);
        return userOptional.orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }
}

