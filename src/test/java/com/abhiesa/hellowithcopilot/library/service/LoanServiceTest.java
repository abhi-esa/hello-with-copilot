package com.abhiesa.hellowithcopilot.library.service;

import com.abhiesa.hellowithcopilot.library.model.*;
import com.abhiesa.hellowithcopilot.library.repository.LoanRepository;
import com.abhiesa.hellowithcopilot.library.exception.BusinessException;
import com.abhiesa.hellowithcopilot.library.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LoanServiceTest {

    @Test
    void testCheckoutLoanSuccess() {
        LoanRepository repo = Mockito.mock(LoanRepository.class);
        BookService bookService = Mockito.mock(BookService.class);
        MemberService memberService = Mockito.mock(MemberService.class);

        Book book = new Book();
        book.setId(1L);
        book.setTotalCopies(6);
        book.setAvailableCopies(2);
        book.setTotalCopies(2);

        Mockito.when(bookService.get(1L)).thenReturn(book);
        Mockito.when(bookService.update(book)).thenReturn(book);

        Loan loan = new Loan(null, 1L, 1L, LocalDate.now(), LocalDate.now().plusDays(14), null, LoanStatus.ACTIVE);
        Mockito.when(repo.save(Mockito.any(Loan.class))).thenReturn(loan);

        LoanService service = new LoanService(repo, bookService, memberService);
        Loan result = service.checkout(1L, 1L, 14);

        assertEquals(LoanStatus.ACTIVE, result.getStatus());
        assertEquals(1L, result.getBookId());
        assertEquals(1L, result.getMemberId());
        assertEquals(1, book.getAvailableCopies());
    }

    @Test
    void testCheckoutLoanNoCopies() {
        LoanRepository repo = Mockito.mock(LoanRepository.class);
        BookService bookService = Mockito.mock(BookService.class);
        MemberService memberService = Mockito.mock(MemberService.class);

        Book book = new Book();
        book.setId(1L);
        book.setAvailableCopies(0);

        Mockito.when(bookService.get(1L)).thenReturn(book);

        LoanService service = new LoanService(repo, bookService, memberService);
        assertThrows(BusinessException.class, () -> service.checkout(1L, 1L, 14));
    }

    @Test
    void testReturnLoanSuccess() {
        LoanRepository repo = Mockito.mock(LoanRepository.class);
        BookService bookService = Mockito.mock(BookService.class);
        MemberService memberService = Mockito.mock(MemberService.class);

        Loan loan = new Loan(1L, 1L, 1L, LocalDate.now(), LocalDate.now().plusDays(14), null, LoanStatus.ACTIVE);
        Book book = new Book();
        book.setId(1L);
        book.setTotalCopies(4);
        book.setAvailableCopies(3);


        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(loan));
        Mockito.when(bookService.get(1L)).thenReturn(book);
        Mockito.when(bookService.update(book)).thenReturn(book);
        Mockito.when(repo.save(Mockito.any(Loan.class))).thenReturn(loan);

        LoanService service = new LoanService(repo, bookService, memberService);
        Loan returned = service.returnLoan(1L);

        assertEquals(LoanStatus.RETURNED, returned.getStatus());
        assertEquals(4, book.getAvailableCopies());
        assertNotNull(returned.getReturnedDate());
    }

    @Test
    void testReturnLoanNotActive() {
        LoanRepository repo = Mockito.mock(LoanRepository.class);
        BookService bookService = Mockito.mock(BookService.class);
        MemberService memberService = Mockito.mock(MemberService.class);

        Loan loan = new Loan(1L, 1L, 1L, LocalDate.now(), LocalDate.now().plusDays(14), null, LoanStatus.RETURNED);

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(loan));

        LoanService service = new LoanService(repo, bookService, memberService);
        assertThrows(BusinessException.class, () -> service.returnLoan(1L));
    }

    @Test
    void testMemberLoans() {
        LoanRepository repo = Mockito.mock(LoanRepository.class);
        BookService bookService = Mockito.mock(BookService.class);
        MemberService memberService = Mockito.mock(MemberService.class);

        List<Loan> loans = List.of(new Loan());
        Mockito.when(repo.findByMemberId(1L)).thenReturn(loans);

        LoanService service = new LoanService(repo, bookService, memberService);
        assertEquals(loans, service.memberLoans(1L));
    }

    @Test
    void testAllLoans() {
        LoanRepository repo = Mockito.mock(LoanRepository.class);
        BookService bookService = Mockito.mock(BookService.class);
        MemberService memberService = Mockito.mock(MemberService.class);

        List<Loan> loans = List.of(new Loan());
        Mockito.when(repo.findAll()).thenReturn(loans);

        LoanService service = new LoanService(repo, bookService, memberService);
        assertEquals(loans, service.allLoans());
    }
}

