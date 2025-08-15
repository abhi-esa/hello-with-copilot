package com.abhiesa.hellowithcopilot.library.service;

import com.abhiesa.hellowithcopilot.library.model.Book;
import com.abhiesa.hellowithcopilot.library.repository.BookRepository;
import com.abhiesa.hellowithcopilot.library.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

  @Test
  void testCreateBook() {
    BookRepository repo = Mockito.mock(BookRepository.class);
    BookService service = new BookService(repo);
    Book book = new Book();
    book.setTotalCopies(5);
    book.setAvailableCopies(0);
    Mockito.when(repo.save(book)).thenReturn(book);
    Book created = service.create(book);
    assertEquals(book, created);
    assertEquals(5, created.getAvailableCopies());
  }

  @Test
  void testGetBookFound() {
    BookRepository repo = Mockito.mock(BookRepository.class);
    BookService service = new BookService(repo);
    Book book = new Book();
    book.setId(1L);
    Mockito.when(repo.findById(1L)).thenReturn(Optional.of(book));
    assertEquals(book, service.get(1L));
  }

  @Test
  void testGetBookNotFound() {
    BookRepository repo = Mockito.mock(BookRepository.class);
    BookService service = new BookService(repo);
    Mockito.when(repo.findById(1L)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> service.get(1L));
  }

  @Test
  void testListBooks() {
    BookRepository repo = Mockito.mock(BookRepository.class);
    BookService service = new BookService(repo);
    List<Book> books = List.of(new Book());
    Mockito.when(repo.findAll()).thenReturn(books);
    assertEquals(books, service.list());
  }

  @Test
  void testUpdateBook() {
    BookRepository repo = Mockito.mock(BookRepository.class);
    BookService service = new BookService(repo);
    Book book = new Book();
    book.setId(1L);
    Mockito.when(repo.findById(1L)).thenReturn(Optional.of(book));
    Mockito.when(repo.save(book)).thenReturn(book);
    assertEquals(book, service.update(book));
  }

  @Test
  void testDeleteBook() {
    BookRepository repo = Mockito.mock(BookRepository.class);
    BookService service = new BookService(repo);
    service.delete(1L);
    Mockito.verify(repo).deleteById(1L);
  }
}