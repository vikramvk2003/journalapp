package com.vikram.journalWebsite.Controller;

import com.vikram.journalWebsite.Entity.JournalEntry;
import com.vikram.journalWebsite.Entity.Users;
import com.vikram.journalWebsite.Service.JournalEntryService;
import com.vikram.journalWebsite.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("journal-api")
public class journalEntryController {
        @Autowired
        private JournalEntryService journalEntryService;

        @Autowired
        private UserService userService;
        @GetMapping
        public  ResponseEntity<?> getAll(){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String userName = authentication.getName();
                Users users = userService.findByUserName(userName);
                List<JournalEntry> all = users.getJournalEntries();
                if (all != null && !all.isEmpty()){
                        return new ResponseEntity<>(all,HttpStatus.OK);
                }
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);


        }
        @PostMapping
        public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
                try {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        String userName = authentication.getName();
                        myEntry.setDate(LocalDateTime.now());
                        journalEntryService.saveEntry(myEntry,userName);
                        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
                }
                catch (Exception e){
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

                }
        }
        @GetMapping("id/{myId}")
        public ResponseEntity<JournalEntry> getData(@PathVariable ObjectId myId) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String userName = authentication.getName();
                Users users = userService.findByUserName(userName);

                // Check if the user's own journal entries contain the ID
                boolean hasEntry = users.getJournalEntries().stream()
                        .anyMatch(x -> x.getId().toString().equals(myId.toString()));

                if (hasEntry) {
                        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
                }

                return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // Or 404 if preferred
        }

        @DeleteMapping("id/{myId}")
        public ResponseEntity<?> deleteData(@PathVariable ObjectId myId ){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String userName = authentication.getName();
                journalEntryService.deleteById(myId,userName);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);


        }
        @PutMapping("id/{myId}")
        public ResponseEntity<?> updateData(
                @PathVariable ObjectId myId ,
                @RequestBody JournalEntry newEntry){
                JournalEntry old=journalEntryService.findById(myId).orElse(null);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String userName = authentication.getName();
                Users users = userService.findByUserName(userName);
                boolean hasEntry = users.getJournalEntries().stream()
                        .anyMatch(x -> x.getId().toString().equals(myId.toString()));

                if (hasEntry) {
                        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                        if (old !=null){

                                old.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().equals("") ? newEntry.getTitle():old.getTitle());
                                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")?newEntry.getContent():old.getContent());
                                journalEntryService.saveEntry(old);
                                return new ResponseEntity<>(old,HttpStatus.OK);
                        }
                }


                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

}
