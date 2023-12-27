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
            
            
    public List<UserEntity> getUser() {

        return userRepository.findAll();
    }
}
