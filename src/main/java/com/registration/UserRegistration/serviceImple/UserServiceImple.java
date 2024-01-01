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

    /**
     * This method is use to fetch all record from database. 
     * @return 
     */
    @Override
    public List<UserEntity> getUser() {

        return userRepository.findAll();
    }

    
    /**
     * This method is us to save user in database.
     * @param email
     * @param mobile
     * @param username
     * @return 
     */
    public String saveUser(String email, String mobile, String username) {

        UserEntity user = setUserEntity(email, mobile, username);

        // Check if email is unique
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Error: Email already exists.";
        }

        // Check if mobile number is valid
        if (!isValidMobileNumber(user.getMobile())) {
            return "Error: Invalid mobile number.";
        }

        //Check if mobile number is unique
        if (userRepository.existsByMobile(user.getMobile())) {
            return "Error: Mobile number already exists.";
        }

        // Save the user
        userRepository.save(user);

        return "User successfully registered.";
    }

    /**
     * This method is use to check mobile number is valid or not.
     * @param mobileNumber
     * @return 
     */
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

/**
 * This method is use to validate an email and send an OTP to provided email. 
 * @param email
 * @throws Exception 
 */  
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

        loginAttempts.remove(email);
    }

    private boolean isEmailValid(String email) {
        return email != null && !email.trim().isEmpty();
    }

    /**
     * This method is use to check login attempts
     * @param email
     * @throws Exception 
     */
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

    /**
     * This method is use to validate the OTP
     * @param email
     * @param enteredOtp
     * @return
     * @throws Exception 
     */
    @Override
    public boolean validateOtp(String email, String enteredOtp) throws Exception {
        // Check if the email is valid
        if (!isEmailValid(email)) {
            throw new Exception("Invalid email.");
        }

        // Check if the user with the provided email exists
        if (!userRepository.existsByEmail(email)) {
            throw new Exception("User not found");
        }

        // Check if entered OTP is valid
        return otpService.validateOtp(email, enteredOtp);
    }
}
