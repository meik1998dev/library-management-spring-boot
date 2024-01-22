package com.example.lib.book;

import com.example.lib.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    private final BookRepository bookRepository; // Assuming you have a repository interface

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Map<String, Object> updates) {
        Book book = bookRepository.findById(id.toString()).orElseThrow(() -> new BookNotFoundException(id.toString()));

        applyUpdatesToBook(book, updates);

        return bookRepository.save(book);
    }

    public boolean deleteBook(String id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
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
