package com.subham.journalApp.service;


import com.subham.journalApp.entity.User;
import com.subham.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void sampleTest(){
        assertEquals(4,2+2);
    }

    @Test
    public void findByUserNameTest(){
        assertNotNull(userRepository.findByUserName("monty"));
    }

    @Disabled
    @Test
    public void findJournalsNotEmptyByUserNameTest(){
        User user = userRepository.findByUserName("monty");
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,3,5",
            "10,2,12"
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
}
