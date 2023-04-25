package com.spotmini.users.controllers;


import com.spotmini.users.entities.UserEntity;
import com.spotmini.users.models.UserModel;
import com.spotmini.users.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String, HashMap<String,String>> kafkaTemplate;


    public UserController(RestTemplate restTemplate, KafkaTemplate<String, HashMap<String,String>> kafkaTemplate) {
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }


    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody UserModel userModel) {
        return userService.createUser(userModel);
    }

    @PostMapping("/login")
    public UserEntity loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserRole(@PathVariable("id") Long userId) {
        userService.updateUserRole(userId);
        return ResponseEntity.ok().body("User role updated successfully.");

    }

    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok().body("User logged out successfully.");
        }
        return ResponseEntity.badRequest().body("No active session found.");
    }

}





