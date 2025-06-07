package net.edigest.journalApp.controller;

import net.edigest.journalApp.entity.Users;
import net.edigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicContoller {
    @Autowired
    private UserService userService;

    @GetMapping
    public String healthCheck(){
        return "ok";
    }
    @PostMapping("/create-user")
    public void createUser(@RequestBody Users user){
        userService.saveEntry(user);
    }
}
