package com.vikram.journalWebsite.Service;


import com.vikram.journalWebsite.Entity.JournalEntry;
import com.vikram.journalWebsite.Entity.Users;
import com.vikram.journalWebsite.Repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry , String userName){
        try {
            Users users = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved =  journalEntryRepository.save(journalEntry);
            users.getJournalEntries().add(saved);
//            users.setUserName(null);
            userService.saveUser(users);
        }
        catch (Exception e){
            log.error("error",e); 
            throw new RuntimeException("An Error has Occured while saving the entry.",e);
        }


    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);

    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);

    }

    @Transactional
    public void deleteById(ObjectId id, String userName){
        try {
            Users users = userService.findByUserName(userName);
            boolean removed = users.getJournalEntries().removeIf(x ->x.getId().equals(id));
            if (removed){
                userService.saveUser(users);
                journalEntryRepository.deleteById(id);
            }
        }
        catch (Exception e){
            throw new RuntimeException("An Error has Occured while saving the entry.\",e");
        }

    }







}
