package net.edigest.journalApp.service;

import net.edigest.journalApp.entity.JournalEntry;
import net.edigest.journalApp.entity.Users;
import net.edigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        Users users = userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        users.getJournalEntries().add(saved);
        userService.saveEntry(users);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getEntry() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findByID(ObjectId myId) {
        return journalEntryRepository.findById(String.valueOf(myId));
    }

    public void deleteByID(String id, String userName) {
        Users users = userService.findByUsername(userName);
        users.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(users);
        journalEntryRepository.deleteById(String.valueOf(id));
    }
}
