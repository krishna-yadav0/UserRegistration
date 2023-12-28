package com.registration.UserRegistration.controller;

import com.registration.UserRegistration.entity.UserEntity;
import com.registration.UserRegistration.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author KRISHNA PRASAD YADAV
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public List<UserEntity> getUser() {

        return userService.getUser();
    }

    @PostMapping("/save/{email}/{mobilenumber}/{username}")
    public ResponseEntity<String> saveUser(@PathVariable String email, @PathVariable String mobilenumber,
            @PathVariable String username) {
        String result = userService.saveUser(email, mobilenumber, username);
        if ("User successfully registered.".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email) {
        // Call the service to handle login logic
        try {
            userService.loginUser(email);
            return ResponseEntity.ok("OTP sent successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
