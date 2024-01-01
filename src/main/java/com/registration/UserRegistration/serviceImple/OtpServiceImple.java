package com.registration.UserRegistration.serviceImple;

import com.registration.UserRegistration.service.OtpService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author KRISHNA PRASAD YADAV
 */
@Service
public class OtpServiceImple implements OtpService {

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> otpMap = new HashMap<>();
    private Map<String, Long> otpCreationTime = new HashMap<>();
    private static final int OTP_EXPIRY_TIME_IN_MILLIS = 2 * 60 * 1000; // 2 minutes

    
    /**
     * This method is use to generate the 6 digit OTP
     * @return 
     */
    public String generateOtp() {
        // Generate a 6-digit OTP 
        int otp = 100000 + (int) (Math.random() * 900000);
        return String.valueOf(otp);
    }

    
    /**
     * This method is use to send OTP on provided email
     * @param email
     * @param otp
     * @throws Exception 
     */
    public void sendOtpByEmail(String email, String otp) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {

            helper.setTo(email);
            helper.setSubject("Your OTP for Login");
            helper.setText("Your OTP is: " + otp);
            
            //Sending an email
            mailSender.send(message);
            otpMap.put(email, otp);
            
            // Record the time the OTP was sent
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            System.out.println(formatter.format(date));
            otpCreationTime.put(email, date.getTime());

        } catch (MessagingException e) {
            throw new Exception("Failed to send OTP. Please try again.", e);
        }
    }

    /**
     * This method is use to validate the OTP.
     * @param email
     * @param enteredOtp
     * @return 
     */
    @Override
    public boolean validateOtp(String email, String enteredOtp) {
        // Check if the entered OTP matches the stored OTP for the given email
        String storedOtp = otpMap.get(email);

        if (storedOtp != null && storedOtp.equals(enteredOtp)) {
            
            // Check if the OTP has not expired
            long creationTime = otpCreationTime.get(email);
            long currentTime = System.currentTimeMillis();

            if (currentTime - creationTime <= OTP_EXPIRY_TIME_IN_MILLIS) {
                // Clear the OTP for the user after successful validation
                otpMap.remove(email);
                otpCreationTime.remove(email);
                return true;
            }
        }

        return false;
    }

}
