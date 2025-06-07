package net.edigest.journalApp.service;

import net.edigest.journalApp.entity.Users;
import net.edigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(Users users){
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRoles(Arrays.asList("USERS"));
        userEntryRepository.save(users);
    }

    public void NewEntry(Users users){
        userEntryRepository.save(users);
    }
    public List<Users> getEntry(){
        return userEntryRepository.findAll();
    }

    public Optional<Users> findByID(ObjectId myId){
        return userEntryRepository.findById(String.valueOf(myId));
    }

    public void deleteByID (ObjectId myId){
        userEntryRepository.deleteById(String.valueOf(myId));
    }
    public void deleteByUsername (String userName){
       Users users = userEntryRepository.findByUsername(userName);
       if (users != null){
           userEntryRepository.delete(users);
       }
    }

    public Users findByUsername(String username){
        return userEntryRepository.findByUsername(username);
    }

}
