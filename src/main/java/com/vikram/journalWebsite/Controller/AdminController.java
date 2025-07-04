package com.vikram.journalWebsite.Controller;

import com.vikram.journalWebsite.Entity.Users;
import com.vikram.journalWebsite.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")

public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("all-users")
    private ResponseEntity<?> getAllUser(){
        List<Users> all = userService.getAll();
        if (all!=null || all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

        @PostMapping("/create-admin-user")
        public void   createUser(@RequestBody Users users) {
         userService.saveNewAdmin(users);
    }





}
