package net.edigest.journalApp.controller;

import net.edigest.journalApp.entity.JournalEntry;
import net.edigest.journalApp.entity.Users;
import net.edigest.journalApp.service.JournalEntryService;
import net.edigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;



    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
        Users users = userService.findByUsername(username);
        List <JournalEntry> all = users.getJournalEntries();
        if( all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findByID(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userName}/{myId}")
    public ResponseEntity<?> deleteUserEntry(@PathVariable String userName, @PathVariable String myId){
        journalEntryService.deleteByID(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteEntry(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName){
        JournalEntry oldEntry = journalEntryService.findByID(myId).orElse(null);
        if (oldEntry != null){
            // Fix your condition here:
            // Current: oldEntry.setTitle(newEntry.getTitle() != null && newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            // This will NOT update title when title is non-empty. It should be:
            if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                oldEntry.setTitle(newEntry.getTitle());
            }
            if (newEntry.getContent() != null) {
                oldEntry.setContent(newEntry.getContent());
            }
            // For date, usually you want to set to "now" or keep old if new is null
            if (newEntry.getDate() != null) {
                oldEntry.setDate(newEntry.getDate());
            } else {
                oldEntry.setDate(LocalDateTime.now());
            }

            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
