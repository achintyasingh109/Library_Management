package com.example.libraryManagmentSystem.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LogInController {
    private static final String USERNAME = "librarian";
    private static final String PASSWORD = "librarian123";

    @PostMapping("/login")
    public String login(@RequestBody UserCredentials credentials) {
        if (USERNAME.equals(credentials.getUsername()) && PASSWORD.equals(credentials.getPassword())) {
            return "Login successful!";
        } else {
            throw new IllegalArgumentException("Wrong username or password!");
        }
    }
    
    public static class UserCredentials {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    
}
