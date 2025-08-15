package com.abhiesa.hellowithcopilot.library.controller;

import com.abhiesa.hellowithcopilot.library.model.Loan;
import com.abhiesa.hellowithcopilot.library.service.LoanService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCheckoutLoan() throws Exception {
        Loan loan = new Loan();
        loan.setId(1L);

        Map<String, Object> req = Map.of("bookId", 1L, "memberId", 1L, "days", 14);

        Mockito.when(loanService.checkout(1L, 1L, 14)).thenReturn(loan);

        mockMvc.perform(post("/api/loans/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testReturnLoan() throws Exception {
        Loan loan = new Loan();
        loan.setId(1L);

        Mockito.when(loanService.returnLoan(1L)).thenReturn(loan);

        mockMvc.perform(post("/api/loans/1/return"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testByMember() throws Exception {
        Loan loan = new Loan();
        loan.setId(1L);

        Mockito.when(loanService.memberLoans(1L)).thenReturn(List.of(loan));

        mockMvc.perform(get("/api/loans/member/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testAllLoans() throws Exception {
        Loan loan = new Loan();
        loan.setId(1L);

        Mockito.when(loanService.allLoans()).thenReturn(List.of(loan));

        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
