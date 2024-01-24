package com.example.lib.patron;

import com.example.lib.exceptions.NoResourceFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "patrons")
    public List<Patron> findAllPatrons() {
        return patronRepository.findAll();
    }

    @Cacheable(value = "patrons" , key = "#id")
    public Patron findPatronById(Long id) {
        return patronRepository.findById(id.toString())
                .orElseThrow(() -> new NoResourceFoundException("Patron not found with id:"+ id));
    }

    @CachePut(value = "patrons" , key = "#patron.id")
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @CachePut(value = "patrons" , key = "#id")
    public Patron updatePatron(Long id, @RequestBody Map<String, Object> updates) {
        Patron patron = patronRepository.findById(id.toString()).orElseThrow(() -> new NoResourceFoundException("Patron not found with id:"+ id));
        patronValidator.validateUpdates(updates);

        applyUpdatesToBook(patron, updates);

        return patronRepository.save(patron);
    }

    @CacheEvict(value = "patrons" , key = "#id")
    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id.toString())) {
            throw new NoResourceFoundException("Patron not found with id:"+ id);
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
