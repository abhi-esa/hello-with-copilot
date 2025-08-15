package com.abhiesa.hellowithcopilot.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;

/**
 * Entity representing a member in the library.
 * Applies defensive programming and bean validation.
 */
@Entity
@Table(name = "members")
@Getter
@Setter
@ToString
public class Member {

    /**
     * Unique identifier for the member.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Member's unique library ID.
     */
    @NotBlank(message = "Member ID cannot be blank")
    private String memberId;

    /**
     * Name of the member.
     */
    @NotBlank(message = "Name cannot be blank")
    private String name;

    /**
     * Email address of the member.
     */
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Date when the member joined.
     */
    @PastOrPresent(message = "Joined date cannot be in the future")
    private LocalDate joined;

    /**
     * Whether the member is currently active.
     */
    private boolean active = true;

    /**
     * Equality based on member id.
     * @param o Other object
     * @return true if ids are equal
     */
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Member member = (Member) o;
      return Objects.equals(id, member.id);
    }

    /**
     * Hash code based on member id.
     * @return hash code
     */
    @Override
    public int hashCode() {
      return Objects.hash(id);
    }
}