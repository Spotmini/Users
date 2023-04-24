package com.spotmini.users.controllers;

import com.spotmini.users.models.UserModel;
import com.spotmini.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserModel registerUser(@RequestBody UserModel userModel) {
        return userService.createUser(userModel);
    }

    @PostMapping("/login")
    public UserModel loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }

    // Update user role
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserRole(@PathVariable("id") Long userId, @RequestBody boolean isSpecial) {
        try {
            userService.updateUserRole(userId, isSpecial);
            return ResponseEntity.ok().body("User role updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}





