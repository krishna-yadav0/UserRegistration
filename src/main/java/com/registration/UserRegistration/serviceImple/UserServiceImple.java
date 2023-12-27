/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registration.UserRegistration.serviceImple;

import com.registration.UserRegistration.entity.UserEntity;
import com.registration.UserRegistration.repository.UserRepository;
import com.registration.UserRegistration.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author KRISHNA PRASAD YADAV
 */
@Service
public class UserServiceImple implements UserService{
    
    
    @Autowired
    private UserRepository userRepository;
            
            
    @Override
    public List<UserEntity> getUser() {

        return userRepository.findAll();
    }
    
    public String saveUser(String email, String mobile, String username) {
        
        UserEntity user = setUserEntity(email, mobile, username);
// Check if email is unique
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Error: Email already exists.";
        }

        // Check if mobile number is valid and unique
        if (!isValidMobileNumber(user.getMobile())) {
            return "Error: Invalid mobile number.";
        }

        if (userRepository.existsByMobile(user.getMobile())) {
            return "Error: Mobile number already exists.";
        }

        // Save the user
        userRepository.save(user);

        return "User successfully registered.";
    }
    
    
    private boolean isValidMobileNumber(String mobileNumber) {
    // Check if the mobile number is not null and is exactly 10 digits
    if (mobileNumber == null || mobileNumber.length() != 10) {
        return false;
    }

    // Check if the mobile number consists of only digits
    for (char digit : mobileNumber.toCharArray()) {
        if (!Character.isDigit(digit)) {
            return false;
        }
    }
    return true;
}
    
    private UserEntity setUserEntity(String email, String mobile, String username){
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setMobile(mobile);
        user.setUsername(username);
        
        return user;
    }
    
}
