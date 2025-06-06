package com.vikram.journalWebsite.Services;

import com.vikram.journalWebsite.Entity.Users;
import com.vikram.journalWebsite.Repository.UserRepository;
import com.vikram.journalWebsite.Service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(Users.builder().userName("vikram").password("fhijdk").roles( new ArrayList<>()).build());
        UserDetails users =     userDetailsService.loadUserByUsername("vikram");
        Assertions.assertNotNull(users);
    }

}
