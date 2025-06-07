package net.edigest.journalApp.service;

import net.edigest.journalApp.entity.Users;
import net.edigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Users users = userRepository.findByUsername(username);
        if (users != null){
            User.builder().username(users.getUsername()).password(users.getPassword())
                    .roles(users.getRoles().toArray(new String[0])).build();
            return users;
        }
        return null;

    }

}
