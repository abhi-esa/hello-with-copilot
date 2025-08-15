package com.abhiesa.hellowithcopilot.library.controller;

import com.abhiesa.hellowithcopilot.library.model.Book;
import com.abhiesa.hellowithcopilot.library.model.Author;
import com.abhiesa.hellowithcopilot.library.model.Category;
import com.abhiesa.hellowithcopilot.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthors(List.of(new Author()));
        book.setCategory(Category.FICTION);
        book.setIsbn("1234567890");
        book.setTotalCopies(10);
        book.setAvailableCopies(10);
        book.setPublishedDate(LocalDate.now());
        book.setAuthors(List.of(new Author(1L, "First Name", "Last Name")));


      Mockito.when(bookService.create(Mockito.any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    void testGetBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        Mockito.when(bookService.get(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    void testListBooks() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        Mockito.when(bookService.list()).thenReturn(List.of(book));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Updated Book");
        book.setIsbn("ISBN1234567890");
        book.setCategory(Category.FICTION);
        book.setAuthors(List.of(new Author(1L, "First Name", "Last Name")));

        Mockito.when(bookService.update(Mockito.any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }
}

