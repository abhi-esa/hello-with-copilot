package com.abhiesa.hellowithcopilot.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Entity representing an author in the library.
 * Applies defensive programming and bean validation.
 */
@Entity
@Table(name = "authors")
@Getter
@Setter
@ToString
public class Author {
  /**
   * Unique identifier for the author.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * First name of the author.
   */
  @NotBlank(message = "First name cannot be blank")
  private String firstName;

  /**
   * Last name of the author.
   */
  @NotBlank(message = "Last name cannot be blank")
  private String lastName;

  /**
   * Default constructor required by JPA.
   */
  public Author() {
    // JPA requires a no-args constructor
  }

  /**
   * Constructs an Author with all fields.
   * @param id Author ID
   * @param firstName First name
   * @param lastName Last name
   */
  public Author(Long id, String firstName, String lastName) {
    this.id = id;
    setFirstName(firstName);
    setLastName(lastName);
  }

  /**
   * Sets the first name of the author.
   * @param firstName First name
   * @throws IllegalArgumentException if firstName is null or blank
   */
  public void setFirstName(String firstName) {
    if (firstName == null || firstName.trim().isEmpty()) {
      throw new IllegalArgumentException("First name cannot be null or empty");
    }
    this.firstName = firstName;
  }

  /**
   * Sets the last name of the author.
   * @param lastName Last name
   * @throws IllegalArgumentException if lastName is null or blank
   */
  public void setLastName(String lastName) {
    if (lastName == null || lastName.trim().isEmpty()) {
      throw new IllegalArgumentException("Last name cannot be null or empty");
    }
    this.lastName = lastName;
  }

  /**
   * Equality based on author id.
   * @param o Other object
   * @return true if ids are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Author author = (Author) o;
    return Objects.equals(id, author.id);
  }

  /**
   * Hash code based on author id.
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}