/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registration.UserRegistration.controller;

import com.registration.UserRegistration.entity.UserEntity;
import com.registration.UserRegistration.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String saveUser(@PathVariable String email, @PathVariable String mobilenumber,
            @PathVariable String username){
        return userService.saveUser(email, mobilenumber, username);
    }
}
