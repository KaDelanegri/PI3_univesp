package com.PI.schoolBot.controler;

import com.PI.schoolBot.service.user.model.User;
import com.PI.schoolBot.service.user.model.UserLogin;
import com.PI.schoolBot.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<User> post(@Valid @RequestBody User user){
        return userService.userRegistration(user);
    }

    @PostMapping("/login")
    public User authentication(@RequestBody UserLogin user){
        return userService.signIn(user);
    }
}