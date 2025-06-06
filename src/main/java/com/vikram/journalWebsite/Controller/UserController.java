package com.vikram.journalWebsite.Controller;

import com.vikram.journalWebsite.Entity.Users;
import com.vikram.journalWebsite.Repository.UserRepository;
import com.vikram.journalWebsite.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users-api")
public class UserController {

        @Autowired
        private UserService userService;

        @Autowired
        private UserRepository userRepository;  // << Add this!

        @PutMapping
        public ResponseEntity<?> updateUser(@RequestBody Users users) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String userName = authentication.getName();
                Users userInDb = userService.findByUserName(userName);
                if (userInDb != null) {
                        userInDb.setUserName(users.getUserName());
                        userInDb.setPassword(users.getPassword());
                        userService.saveNewUser(userInDb);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        @DeleteMapping
        public ResponseEntity<?> deleteUserById() {
                try {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        String username = authentication.getName();
                        Users user = userRepository.findByUserName(username);
                        if (user == null) {
                                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                        }
                        userRepository.delete(user);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                        e.printStackTrace();
                        return new ResponseEntity<>("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
}
