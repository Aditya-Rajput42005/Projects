package net.edigest.journalApp.controller;


import net.edigest.journalApp.entity.Users;
import net.edigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserEntryController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Users> getAll(){
        return userService.getEntry();
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody Users users) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Users userInDb = userService.findByUsername(username);
            if (userInDb != null) {
                userInDb.setUsername(users.getUsername());
                userInDb.setPassword(users.getPassword());
                userService.saveEntry(userInDb);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            else {
                return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

//    @DeleteMapping
//    public ResponseEntity<?> deleteUser(@RequestBody Users users){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//    }
}
