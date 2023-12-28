/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.registration.UserRegistration.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author KRISHNA PRASAD YADAV
 */
@Service
public interface OtpService {
     public String generateOtp();
     public void sendOtpByEmail(String email, String otp) throws Exception;
}
