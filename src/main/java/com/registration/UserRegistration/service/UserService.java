/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.registration.UserRegistration.service;

import com.registration.UserRegistration.entity.UserEntity;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author KRISHNA PRASAD YADAV
 */
@Service
public interface UserService {
    public List<UserEntity> getUser();
     public String saveUser(String email, String mobilenumber, String username);  
}
