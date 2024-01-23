package com.example.lib.book;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookValidator bookValidator;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllBooks_ShouldReturnAllBooks() {
        List<Book> expectedBooks = Arrays.asList(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.findAllBooks();

        assertNotNull(actualBooks);
        assertEquals(expectedBooks.size(), actualBooks.size());
        verify(bookRepository).findAll();
    }

    @Test
    void findBookById_ShouldReturnBook() {
        String bookId = "1";
        Book expectedBook = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookService.findBookById(bookId);

        assertNotNull(actualBook);
        assertEquals(expectedBook, actualBook);
        verify(bookRepository).findById(bookId);
    }

    @Test
    void addBook_ShouldSaveBook() {
        Book newBook = new Book();
        when(bookRepository.save(newBook)).thenReturn(newBook);

        Book savedBook = bookService.addBook(newBook);

        assertNotNull(savedBook);
        verify(bookRepository).save(newBook);
    }

    @Test
    void updateBook_ShouldUpdateBook() {
        Long bookId = 1L;
        Book existingBook = new Book();
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", "Updated Title");

        when(bookRepository.findById(bookId.toString())).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        Book updatedBook = bookService.updateBook(bookId, updates);

        assertNotNull(updatedBook);
        verify(bookRepository).findById(bookId.toString());
        verify(bookValidator).validateUpdates(updates);
        verify(bookRepository).save(existingBook);
    }

    @Test
    void deleteBook_ShouldDeleteBook() {
        String bookId = "1";
        when(bookRepository.existsById(bookId)).thenReturn(true);

        boolean result = bookService.deleteBook(bookId);

        assertTrue(result);
        verify(bookRepository).existsById(bookId);
        verify(bookRepository).deleteById(bookId);
    }
}
