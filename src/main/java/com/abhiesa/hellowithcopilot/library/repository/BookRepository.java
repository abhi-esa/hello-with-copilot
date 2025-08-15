package com.abhiesa.hellowithcopilot.library.repository;

import com.abhiesa.hellowithcopilot.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Book entity.
 * Provides CRUD operations for books.
 * Defensive programming: always validate input parameters in custom methods.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}