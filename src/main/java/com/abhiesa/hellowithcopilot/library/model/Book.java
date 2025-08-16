package com.abhiesa.hellowithcopilot.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * Entity representing a book in the library.
 * Applies defensive programming and bean validation.
 */
@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
public class Book {
    /**
     * Unique identifier for the book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the book.
     */
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    /**
     * List of authors for the book.
     */
    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Exclude
    @NotNull(message = "Authors list cannot be null")
    @Size(min = 1, message = "Book must have at least one author")
    private List<Author> authors;

    /**
     * Category of the book.
     */
    @NotNull(message = "Category cannot be null")
    private Category category;

    /**
     * ISBN of the book.
     */
    @NotBlank(message = "ISBN cannot be blank")
    @Size(max = 20, message = "ISBN must be at most 20 characters")
    private String isbn;

    /**
     * Total number of copies of the book.
     */
    @PositiveOrZero(message = "Total copies cannot be negative")
    private int totalCopies;

    /**
     * Number of available copies of the book.
     */
    @PositiveOrZero(message = "Available copies cannot be negative")
    private int availableCopies;

    /**
     * Date when the book was published.
     */
    @PastOrPresent(message = "Published date cannot be in the future")
    private LocalDate publishedDate;

    /**
     * Default constructor required by JPA.
     */
    public Book() {
      // JPA requires a no-args constructor
    }

    /**
     * Sets the authors for the book.
     * @param authors List of authors
     * @throws IllegalArgumentException if authors is null or empty
     */
    public void setAuthors(List<Author> authors) {
      if (authors == null || authors.isEmpty()) {
        throw new IllegalArgumentException("Authors list cannot be null or empty");
      }
      this.authors = List.copyOf(authors);
    }

    /**
     * Gets the authors for the book.
     * @return Unmodifiable list of authors
     */
    public List<Author> getAuthors() {
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

    /**
     * Equality based on book id.
     * @param o Other object
     * @return true if ids are equal
     */
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Book book = (Book) o;
      return Objects.equals(id, book.id);
    }

    /**
     * Hash code based on book id.
     * @return hash code
     */
    @Override
    public int hashCode() {
      return Objects.hash(id);
    }
}