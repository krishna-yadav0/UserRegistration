/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registration.UserRegistration.serviceImple;

import com.registration.UserRegistration.service.OtpService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author KRISHNA PRASAD YADAV
 */
@Service
public class OtpServiceImple implements OtpService{
    
    @Autowired
    private JavaMailSender mailSender;
    
     public String generateOtp() {
        // Generate a 6-digit OTP (you can customize this based on your requirements)
        // For simplicity, using a random number for demonstration purposes
        int otp = 100000 + (int) (Math.random() * 900000);
        return String.valueOf(otp);
    }

    public void sendOtpByEmail(String email, String otp) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom("mahikesh1999@gmail.com");
            helper.setTo(email);
            helper.setSubject("Your OTP for Login");
            helper.setText("Your OTP is: " + otp);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new Exception("Failed to send OTP. Please try again.", e);
        }
    }
    
}
