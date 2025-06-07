package net.edigest.journalApp.repository;

import net.edigest.journalApp.entity.JournalEntry;
import net.edigest.journalApp.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String> {
    Users findByUsername(String username);
    Users deleteByUsername(String username);
}
