package com.example.lib.patron;

import com.example.lib.book.Book;
import com.example.lib.book.BookValidator;
import com.example.lib.exceptions.BookNotFoundException;
import com.example.lib.exceptions.PatronNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class PatronService {

    private final PatronRepository patronRepository; // Assuming you have a PatronRepository
    private final PatronValidator patronValidator;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronValidator = new PatronValidator();
        this.patronRepository = patronRepository;
    }

    public List<Patron> findAllPatrons() {
        return patronRepository.findAll();
    }

    public Patron findPatronById(Long id) {
        return patronRepository.findById(id.toString())
                .orElseThrow(() -> new PatronNotFoundException(id));
    }

    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public Patron updatePatron(Long id, @RequestBody Map<String, Object> updates) {
        Patron patron = patronRepository.findById(id.toString()).orElseThrow(() -> new PatronNotFoundException(id));
        patronValidator.validateUpdates(updates);

        applyUpdatesToBook(patron, updates);

        return patronRepository.save(patron);
    }

    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id.toString())) {
            throw new PatronNotFoundException(id);
        }
        patronRepository.deleteById(id.toString());
    }

    private void applyUpdatesToBook(Patron patron, Map<String, Object> updates) {
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            switch (entry.getKey()) {
                case "name":
                    if (entry.getValue() instanceof String) {
                        patron.setName((String) entry.getValue());
                    }
                    break;
                case "contactInformation":
                    if (entry.getValue() instanceof String) {
                        patron.setContactInformation((String) entry.getValue());
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
