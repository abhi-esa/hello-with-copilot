package com.abhiesa.hellowithcopilot.library.controller;

import com.abhiesa.hellowithcopilot.library.model.Book;
import com.abhiesa.hellowithcopilot.library.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing books in the library. Applies defensive programming, bean
 * validation, and comprehensive documentation.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

  private final BookService bookService;

  /**
   * Constructs a BookController with the given BookService.
   *
   * @param bookService the service to manage books
   * @throws IllegalArgumentException if bookService is null
   */
  public BookController(BookService bookService) {
    if (bookService == null) {
      throw new IllegalArgumentException("BookService cannot be null");
    }
    this.bookService = bookService;
  }

  /**
   * Creates a new book.
   *
   * @param book the book to create
   * @return ResponseEntity containing the created book or bad request if input is invalid
   */
  @PostMapping
  public ResponseEntity<Book> create(@Valid @RequestBody Book book) {
    if (book == null) {
      return ResponseEntity.badRequest().build();
    }
    Book created = bookService.create(book);
    return ResponseEntity.ok(created);
  }

  /**
   * Retrieves a book by its ID.
   *
   * @param id the ID of the book
   * @return ResponseEntity containing the found book, not found, or bad request if ID is invalid
   */
  @GetMapping("/{id}")
  public ResponseEntity<Book> get(@PathVariable Long id) {
    if (id == null || id <= 0) {
      return ResponseEntity.badRequest().build();
    }
    Book found = bookService.get(id);
    if (found == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(found);
  }

  /**
   * Lists all books.
   *
   * @return ResponseEntity containing the list of books
   */
  @GetMapping
  public ResponseEntity<List<Book>> list() {
    List<Book> books = bookService.list();
    return ResponseEntity.ok(books == null ? List.of() : books);
  }

  /**
   * Updates an existing book by its ID.
   *
   * @param id   the ID of the book to update
   * @param book the updated book data
   * @return ResponseEntity containing the updated book, not found, or bad request if input is
   * invalid
   */
  @PutMapping("/{id}")
  public ResponseEntity<Book> update(@PathVariable Long id, @Valid @RequestBody Book book) {
    if (id == null || id <= 0 || book == null) {
      return ResponseEntity.badRequest().build();
    }
    book.setId(id);
    Book updated = bookService.update(book);
    if (updated == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updated);
  }

  /**
   * Deletes a book by its ID.
   *
   * @param id the ID of the book to delete
   * @return ResponseEntity with no content, or bad request if ID is invalid
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (id == null || id <= 0) {
      return ResponseEntity.badRequest().build();
    }
    bookService.delete(id);
    return ResponseEntity.noContent().build();
  }
}