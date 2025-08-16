package com.abhiesa.hellowithcopilot.library.service;

import com.abhiesa.hellowithcopilot.library.model.*;
import com.abhiesa.hellowithcopilot.library.repository.*;
import com.abhiesa.hellowithcopilot.library.exception.*;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

/**
 * Service class for managing loans.
 * Applies defensive programming, bean validation, and provides comprehensive documentation.
 */
@Service
public class LoanService {
  private final LoanRepository loans;
  private final BookService bookService;
  private final MemberService memberService;

  /**
   * Constructs a LoanService with required dependencies.
   * @param loans LoanRepository instance
   * @param bookService BookService instance
   * @param memberService MemberService instance
   * @throws IllegalArgumentException if any dependency is null
   */
  public LoanService(LoanRepository loans, BookService bookService, MemberService memberService) {
    if (loans == null || bookService == null || memberService == null) {
      throw new IllegalArgumentException("All dependencies must be non-null");
    }
    this.loans = loans;
    this.bookService = bookService;
    this.memberService = memberService;
  }

  /**
   * Checks out a book for a member.
   * @param bookId Book ID
   * @param memberId Member ID
   * @param days Number of days for the loan
   * @return persisted Loan
   * @throws IllegalArgumentException if any parameter is invalid
   * @throws BusinessException if no copies are available
   */
  public Loan checkout(Long bookId, Long memberId, int days) {
    if (bookId == null || bookId <= 0) {
      throw new IllegalArgumentException("Book ID must be positive");
    }
    if (memberId == null || memberId <= 0) {
      throw new IllegalArgumentException("Member ID must be positive");
    }
    if (days <= 0) {
      throw new IllegalArgumentException("Loan days must be positive");
    }
    Book book = bookService.get(bookId);
    memberService.get(memberId); // validate member exists
    if (book.getAvailableCopies() <= 0) throw new BusinessException("No copies available");
    book.setAvailableCopies(book.getAvailableCopies() - 1);
    bookService.update(book);
    Loan loan = new Loan(null, bookId, memberId, LocalDate.now(), LocalDate.now().plusDays(days), null, LoanStatus.ACTIVE);
    return loans.save(loan);
  }

  /**
   * Returns a loaned book.
   * @param loanId Loan ID
   * @return updated Loan
   * @throws IllegalArgumentException if loanId is invalid
   * @throws BusinessException if loan is not active
   * @throws NotFoundException if loan not found
   */
  public Loan returnLoan(Long loanId) {
    if (loanId == null || loanId <= 0) {
      throw new IllegalArgumentException("Loan ID must be positive");
    }
    Loan loan = loans.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found: " + loanId));
    if (loan.getStatus() != LoanStatus.ACTIVE) throw new BusinessException("Loan is not active");
    loan.setReturnedDate(LocalDate.now());
    loan.setStatus(LoanStatus.RETURNED);
    Book book = bookService.get(loan.getBookId());
    book.setAvailableCopies(book.getAvailableCopies() + 1);
    bookService.update(book);
    return loans.save(loan);
  }

  /**
   * Retrieves all loans for a specific member.
   * @param memberId Member ID
   * @return List of loans for the member
   * @throws IllegalArgumentException if memberId is invalid
   */
  public List<Loan> memberLoans(Long memberId) {
    if (memberId == null || memberId <= 0) {
      throw new IllegalArgumentException("Member ID must be positive");
    }
    return loans.findByMemberId(memberId);
  }

  /**
   * Retrieves all loans in the system.
   * @return List of all loans
   */
  public List<Loan> allLoans() {
    return loans.findAll();
  }
}