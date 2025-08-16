package com.abhiesa.hellowithcopilot.library.service;

import com.abhiesa.hellowithcopilot.library.model.Book;
import com.abhiesa.hellowithcopilot.library.repository.BookRepository;
import com.abhiesa.hellowithcopilot.library.exception.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

/**
 * Service class for managing books.
 * Applies defensive programming, bean validation, and provides comprehensive documentation.
 */
@Service
public class BookService {
  private final BookRepository books;

  /**
   * Constructs a BookService with the given BookRepository.
   * @param books BookRepository instance
   * @throws IllegalArgumentException if books is null
   */
  public BookService(BookRepository books) {
    if (books == null) {
      throw new IllegalArgumentException("BookRepository cannot be null");
    }
    this.books = books;
  }

  /**
   * Creates a new book.
   * @param b Book entity
   * @return persisted Book
   * @throws IllegalArgumentException if book is null
   */
  public Book create(@Valid Book b) {
    if (b == null) {
      throw new IllegalArgumentException("Book cannot be null");
    }
    if (b.getAvailableCopies() == 0) b.setAvailableCopies(b.getTotalCopies());
    return books.save(b);
  }

  /**
   * Retrieves a book by its ID.
   * @param id Book ID
   * @return Book entity
   * @throws IllegalArgumentException if id is null or not positive
   * @throws NotFoundException if book not found
   */
  public Book get(Long id) {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException("Book ID must be positive");
    }
    return books.findById(id).orElseThrow(() -> new NotFoundException("Book not found: " + id));
  }

  /**
   * Lists all books.
   * @return List of books
   */
  public List<Book> list() {
    return books.findAll();
  }

  /**
   * Updates an existing book.
   * @param b Book entity
   * @return updated Book
   * @throws IllegalArgumentException if book is null or id is invalid
   */
  public Book update(@Valid Book b) {
    if (b == null || b.getId() == null || b.getId() <= 0) {
      throw new IllegalArgumentException("Book or Book ID is invalid");
    }
    get(b.getId());
    return books.save(b);
  }

  /**
   * Deletes a book by its ID.
   * @param id Book ID
   * @throws IllegalArgumentException if id is invalid
   */
  public void delete(Long id) {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException("Book ID must be positive");
    }
    books.deleteById(id);
  }
}