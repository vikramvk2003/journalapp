package com.vikram.journalWebsite.Services;

import com.vikram.journalWebsite.Repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Disabled
    public void testUserById(){
//        assertEquals(3,2+2);
        assertNotNull(userRepository.findByUserName("vamsee"));

    }
    @Disabled
    @ParameterizedTest
    @CsvSource({
            "2,5,7",
            "7,5,7",
            "5,5,10"
    })
    public void test(int a , int b , int expected){
        assertEquals(expected,a+b);
    }

}
