package com.abhiesa.hellowithcopilot.library.controller;

import com.abhiesa.hellowithcopilot.library.model.Loan;
import com.abhiesa.hellowithcopilot.library.service.LoanService;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing loan operations in the library. Applies defensive programming and
 * comprehensive documentation.
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

  private final LoanService loans;

  /**
   * Constructs a LoanController with the given LoanService.
   *
   * @param loans the service to manage loans
   * @throws IllegalArgumentException if loans is null
   */
  public LoanController(LoanService loans) {
    if (loans == null) {
      throw new IllegalArgumentException("LoanService cannot be null");
    }
    this.loans = loans;
  }

  /**
   * Checks out a book for a member.
   *
   * @param req a map containing "bookId", "memberId", and optional "days"
   * @return ResponseEntity containing the created Loan or bad request if input is invalid
   */
  @PostMapping("/checkout")
  public ResponseEntity<Loan> checkout(@RequestBody Map<String, Object> req) {
    if (req == null || !req.containsKey("bookId") || !req.containsKey("memberId")) {
      return ResponseEntity.badRequest().build();
    }
    try {
      long bookId = Long.parseLong(req.get("bookId").toString());
      long memberId = Long.parseLong(req.get("memberId").toString());
      int days = req.containsKey("days") ? Integer.parseInt(req.get("days").toString()) : 14;
      if (bookId <= 0 || memberId <= 0 || days <= 0) {
        return ResponseEntity.badRequest().build();
      }
      Loan loan = loans.checkout(bookId, memberId, days);
      if (loan == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(loan);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Returns a loaned book.
   *
   * @param id the ID of the loan to return
   * @return ResponseEntity containing the updated Loan or bad request if ID is invalid
   */
  @PostMapping("/{id}/return")
  public ResponseEntity<Loan> returnLoan(@PathVariable Long id) {
    if (id == null || id <= 0) {
      return ResponseEntity.badRequest().build();
    }
    Loan loan = loans.returnLoan(id);
    if (loan == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(loan);
  }

  /**
   * Retrieves all loans for a specific member.
   *
   * @param memberId the ID of the member
   * @return ResponseEntity containing the list of Loans for the member or bad request if ID is
   * invalid
   */
  @GetMapping("/member/{memberId}")
  public ResponseEntity<List<Loan>> byMember(@PathVariable Long memberId) {
    if (memberId == null || memberId <= 0) {
      return ResponseEntity.badRequest().build();
    }
    List<Loan> memberLoans = loans.memberLoans(memberId);
    return ResponseEntity.ok(memberLoans == null ? List.of() : memberLoans);
  }

  /**
   * Retrieves all loans in the system.
   *
   * @return ResponseEntity containing the list of all Loans
   */
  @GetMapping
  public ResponseEntity<List<Loan>> all() {
    List<Loan> allLoans = loans.allLoans();
    return ResponseEntity.ok(allLoans == null ? List.of() : allLoans);
  }
}