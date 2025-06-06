package com.vikram.journalWebsite.Service;


import com.vikram.journalWebsite.Entity.JournalEntry;
import com.vikram.journalWebsite.Entity.Users;
import com.vikram.journalWebsite.Repository.JournalEntryRepository;
import com.vikram.journalWebsite.Repository.UserRepository;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public boolean saveNewUser(Users user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }
        catch (Exception e){
            logger.error("error occure",e);
            return false;
        }
    }
    public void saveNewAdmin(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(Users user){
        userRepository.save(user);
    }
    public List<Users> getAll(){
        return userRepository.findAll();
    }
    public Optional<Users> findById(ObjectId id){
        return userRepository.findById(id);

    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public Users findByUserName(String username){
        return userRepository.findByUserName(username);
    }



}
