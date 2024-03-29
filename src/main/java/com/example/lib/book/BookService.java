package com.example.lib.book;

import com.example.lib.exceptions.NoResourceFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    @Autowired
    public BookService(BookRepository bookRepository , BookValidator bookValidator) {
        this.bookRepository = bookRepository;
        this.bookValidator = bookValidator;
    }

    @Cacheable(value = "books")
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "books", key = "#id")
    public Book findBookById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoResourceFoundException("Book not found with id: " + id));
    }

    @CachePut(value = "books", key = "#book.id")
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }


    @CachePut(value = "books", key = "#id")
    public Book updateBook(Long id, Map<String, Object> updates) {
        Book book = bookRepository.findById(id.toString()).orElseThrow(() -> new NoResourceFoundException("Book not found with id: " + id));

        bookValidator.validateUpdates(updates);
        applyUpdatesToBook(book, updates);

        return bookRepository.save(book);
    }

    @CacheEvict(value = "books", key = "#id")
    public boolean deleteBook(String id) {
        if (!bookRepository.existsById(id)) {
            throw new NoResourceFoundException("Book not found with id: " + id);
        }

        bookRepository.deleteById(id);
        return true;
    }

    private void applyUpdatesToBook(Book book, Map<String, Object> updates) {
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            switch (entry.getKey()) {
                case "title":
                    if (entry.getValue() instanceof String) {
                        book.setTitle((String) entry.getValue());
                    }
                    break;
                case "author":
                    if (entry.getValue() instanceof String) {
                        book.setAuthor((String) entry.getValue());
                    }
                    break;
                case "publicationYear":
                    if (entry.getValue() instanceof Integer) {
                        book.setPublicationYear((Integer) entry.getValue());
                    }
                    break;
                case "ISBN":
                    if (entry.getValue() instanceof String) {
                        book.setISBN((String) entry.getValue());
                    }
                    break;
                default:
                    break;
            }
        }
    }


}
