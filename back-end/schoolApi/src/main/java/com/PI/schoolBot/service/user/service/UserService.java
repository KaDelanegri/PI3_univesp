package com.PI.schoolBot.service.user.service;

import com.PI.schoolBot.exceptions.SchoolAPIException;
import com.PI.schoolBot.service.user.model.User;
import com.PI.schoolBot.service.user.model.UserLogin;
import com.PI.schoolBot.service.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    UserService(final UserRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<User> userRegistration(User user){
        validateRegistration(user.getToken());
        if (repository.findByEmail(user.getEmail()) == null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String  passwordEncoder = encoder.encode(user.getPassword());
            user.setPassword(passwordEncoder);
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
        }
        throw SchoolAPIException.conflict();
    }

    public User signIn(UserLogin userLogin){
        var encoder = new BCryptPasswordEncoder();
        Optional<User> user = repository.findUserByEmail(userLogin.getEmail());
        if (!user.isPresent()){
            throw SchoolAPIException.notFound("user");
        }
        if (encoder.matches(userLogin.getPassword(), user.get().getPassword())){ return user.get(); }
        throw SchoolAPIException.unauthorized();
    }

    private void validateRegistration(final String token) {
        if (!token.equals("987654321"))
            throw SchoolAPIException.forbidden();
    }
}