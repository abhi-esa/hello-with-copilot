package com.abhiesa.hellowithcopilot.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

/**
 * Entity representing a loan in the library.
 * Applies defensive programming and bean validation.
 */
@Entity
@Table(name = "loans")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    /**
     * Unique identifier for the loan.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Member ID associated with the loan.
     */
    @NotNull(message = "Member ID cannot be null")
    private Long memberId;

    /**
     * Book ID associated with the loan.
     */
    @NotNull(message = "Book ID cannot be null")
    private Long bookId;

    /**
     * Date when the loan was created.
     */
    @PastOrPresent(message = "Loan date cannot be in the future")
    private LocalDate loanDate;

    /**
     * Due date for returning the book.
     */
    @NotNull(message = "Due date cannot be null")
    private LocalDate dueDate;

    /**
     * Date when the book was returned.
     */
    private LocalDate returnedDate;

    /**
     * Status of the loan.
     */
    @NotNull(message = "Loan status cannot be null")
    private LoanStatus status;

    /**
     * Equality based on loan id.
     * @param o Other object
     * @return true if ids are equal
     */
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Loan loan = (Loan) o;
      return Objects.equals(id, loan.id);
    }

    /**
     * Hash code based on loan id.
     * @return hash code
     */
    @Override
    public int hashCode() {
      return Objects.hash(id);
    }
}