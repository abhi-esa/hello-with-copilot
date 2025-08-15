package com.abhiesa.hellowithcopilot.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abhiesa.hellowithcopilot.library.model.Loan;
import java.util.List;

/**
 * Repository interface for Loan entity.
 * Provides CRUD operations and custom queries for loans.
 * Defensive programming: validate input parameters in custom methods.
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {
    /**
     * Finds loans by member ID.
     * @param memberId the ID of the member (must not be null)
     * @return list of loans for the member
     * @throws IllegalArgumentException if memberId is null
     */
    default List<Loan> findByMemberIdSafe(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId must not be null");
        }
        return findByMemberId(memberId);
    }

    List<Loan> findByMemberId(Long memberId);
}
