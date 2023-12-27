package com.registration.UserRegistration.repository;

import com.registration.UserRegistration.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KRISHNA PRASAD YADAV
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    public Boolean existsByEmail(String email);
    public Boolean existsByMobile(String mobile_number);

}
