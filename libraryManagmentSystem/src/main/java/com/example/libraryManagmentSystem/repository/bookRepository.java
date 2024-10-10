package com.example.libraryManagmentSystem.repository;


import com.example.libraryManagmentSystem.entity.book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Repository
public interface bookRepository extends JpaRepository<book, Long> {
    List<book> findByNameContaining(String name);
    List<book> findByAuthorContaining(String author);

    default List<book> findByNameOrAuthorContaining(String query) {
        List<book> booksByName = findByNameContaining(query);
        List<book> booksByAuthor = findByAuthorContaining(query);

        // Combine both lists and avoid duplicates
        booksByName.addAll(booksByAuthor);

        // Use a Set to remove duplicates if necessary
        return new ArrayList<>(new HashSet<>(booksByName));
    }
}

