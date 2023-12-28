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
    
    public boolean validateOtp(String email,String ententeredOtp) throws Exception;
}
