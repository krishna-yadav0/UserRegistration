package com.registration.UserRegistration.serviceImple;

import com.registration.UserRegistration.entity.UserEntity;
import com.registration.UserRegistration.repository.UserRepository;
import com.registration.UserRegistration.service.OtpService;
import com.registration.UserRegistration.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author KRISHNA PRASAD YADAV
 */
@Service
public class UserServiceImple implements UserService {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    private Map<String, Integer> loginAttempts = new HashMap<>();
    private Map<String, Long> lastOtpSentTime = new HashMap<>();
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final int BLOCK_TIME_IN_MILLIS = 5 * 60 * 1000; // 5 minutes in milliseconds

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

    private UserEntity setUserEntity(String email, String mobile, String username) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setMobile(mobile);
        user.setUsername(username);

        return user;
    }

//    @Override
//    public String loginUser(String email) {
//return null;
//    }
//    
    @Override
    public void loginUser(String email) throws Exception {
        // Check if the email is valid
        if (!isEmailValid(email)) {
            throw new Exception("Invalid email.");
        }

        // Check if the user with the provided email exists        
        if (!userRepository.existsByEmail(email)) {
            throw new Exception("User not found");
        }

        // Check if the user is blocked
        checkLoginAttempts(email);

        // Generate and send OTP
        String otp = otpService.generateOtp();
        otpService.sendOtpByEmail(email, otp);

        // Record the time the OTP was sent
        lastOtpSentTime.put(email, System.currentTimeMillis());

        // Clear login attempts for the user
        loginAttempts.remove(email);
    }

    private boolean isEmailValid(String email) {
        // Add logic to check if the email is valid (you can use a regular expression)
        // For simplicity, assuming any non-empty string is a valid email
        return email != null && !email.trim().isEmpty();
    }

    private void checkLoginAttempts(String email) throws Exception {
        if (loginAttempts.containsKey(email)) {
            int attempts = loginAttempts.get(email);

            if (attempts >= MAX_LOGIN_ATTEMPTS) {
                long blockTime = lastOtpSentTime.get(email) + BLOCK_TIME_IN_MILLIS;
                long currentTime = System.currentTimeMillis();

                if (currentTime < blockTime) {
                    throw new Exception("Account is blocked. Please try after 5 minutes.");
                } else {
                    // Unblock the account if the block time has passed
                    loginAttempts.remove(email);
                }
            }
        }
    }

}
