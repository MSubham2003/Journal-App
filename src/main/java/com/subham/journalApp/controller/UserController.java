package com.subham.journalApp.controller;

import com.subham.journalApp.entity.User;
import com.subham.journalApp.repository.UserRepository;
import com.subham.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String abc() {
        return "Hello";
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user1 = userService.findByUserName(username);
        user1.setUserName(user.getUserName());
        user1.setPassword(user.getPassword());
        userService.saveNewUser(user1);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        userRepository.deleteByUserName(name);
        return new ResponseEntity<>("Deleted User with username "+name,HttpStatus.NO_CONTENT);
    }

}
