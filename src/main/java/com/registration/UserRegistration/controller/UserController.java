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

    /**
     * This method is use to fetch all record from database(additional)
     * @return 
     */
    @GetMapping("/get")
    public List<UserEntity> getUser() {
        return userService.getUser();
    }

    /**
     * This method is use to save the records in database
     * @param email
     * @param mobilenumber
     * @param username
     * @return 
     */
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

    /**
     * This method is use to send OTP on registered email
     * @param email
     * @return 
     */
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

    
    /**
     * This method is use to validate the OTP and login successfully.
     * @param email
     * @param otp
     * @return 
     */
    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestParam String email, @RequestParam String otp) {
        try {
            // Call the service to handle OTP validation logic
            if (userService.validateOtp(email, otp)) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.badRequest().body("Error: Invalid OTP.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
