package com.subham.journalApp.controller;

import com.subham.journalApp.entity.User;
import com.subham.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getAllUsers();
        if(users!=null && !users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>("NO Users in the DB",HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            userService.save(user);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>("Unable to create user\n"+e.getMessage(),HttpStatus.OK);
        }
    }

    @PutMapping("{username}")
    public ResponseEntity<String> updateUser(@RequestBody User user,@PathVariable String username) {
        try {
            User user1 = userService.findByUserName(username);
            if (user1 != null) {
                user1.setUserName(user.getUserName());
                user1.setPassword(user.getPassword());
                userService.save(user1);
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No User Found with username "+username, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
