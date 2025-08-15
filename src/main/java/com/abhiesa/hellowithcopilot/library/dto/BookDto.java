package com.abhiesa.hellowithcopilot.library.dto;

import com.abhiesa.hellowithcopilot.library.model.Category;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object for Book.
 * Applies defensive programming and provides comprehensive documentation.
 */
@Data
public class BookDto {
  /**
   * Unique identifier for the book.
   */
  private Long id;

  /**
   * Title of the book.
   */
  private String title;

  /**
   * List of author names.
   */
  private List<String> authors;

  /**
   * Category of the book.
   */
  private Category category;

  /**
   * ISBN of the book.
   */
  private String isbn;

  /**
   * Total number of copies of the book.
   */
  private int totalCopies;

  /**
   * Number of available copies of the book.
   */
  private int availableCopies;

  /**
   * Date when the book was published.
   */
  private LocalDate publishedDate;

  /**
   * Sets the authors for the book.
   * @param authors List of author names
   * @throws IllegalArgumentException if authors is null or empty
   */
  public void setAuthors(List<String> authors) {
    if (authors == null || authors.isEmpty()) {
      throw new IllegalArgumentException("Authors list cannot be null or empty");
    }
    this.authors = List.copyOf(authors);
  }

  /**
   * Gets the authors for the book.
   * @return Unmodifiable list of author names
   */
  public List<String> getAuthors() {
    return authors == null ? List.of() : List.copyOf(authors);
  }

  /**
   * Sets the title of the book.
   * @param title Title string
   * @throws IllegalArgumentException if title is null or blank
   */
  public void setTitle(String title) {
    if (title == null || title.trim().isEmpty()) {
      throw new IllegalArgumentException("Title cannot be null or empty");
    }
    this.title = title;
  }

  /**
   * Sets the ISBN of the book.
   * @param isbn ISBN string
   * @throws IllegalArgumentException if ISBN is null or blank
   */
  public void setIsbn(String isbn) {
    if (isbn == null || isbn.trim().isEmpty()) {
      throw new IllegalArgumentException("ISBN cannot be null or empty");
    }
    this.isbn = isbn;
  }

  /**
   * Sets the total number of copies.
   * @param totalCopies Total copies
   * @throws IllegalArgumentException if totalCopies is negative
   */
  public void setTotalCopies(int totalCopies) {
    if (totalCopies < 0) {
      throw new IllegalArgumentException("Total copies cannot be negative");
    }
    this.totalCopies = totalCopies;
  }

  /**
   * Sets the number of available copies.
   * @param availableCopies Available copies
   * @throws IllegalArgumentException if availableCopies is negative or greater than totalCopies
   */
  public void setAvailableCopies(int availableCopies) {
    if (availableCopies < 0 || availableCopies > totalCopies) {
      throw new IllegalArgumentException("Available copies must be between 0 and total copies");
    }
    this.availableCopies = availableCopies;
  }

  /**
   * Sets the published date.
   * @param publishedDate Date published
   * @throws IllegalArgumentException if publishedDate is in the future
   */
  public void setPublishedDate(LocalDate publishedDate) {
    if (publishedDate != null && publishedDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Published date cannot be in the future");
    }
    this.publishedDate = publishedDate;
  }
}