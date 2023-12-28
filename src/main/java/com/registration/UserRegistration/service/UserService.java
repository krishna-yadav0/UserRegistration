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

    public void loginUser(String email) throws Exception;
    
    public boolean validateOtp(String emil, String otp) throws Exception;
}
