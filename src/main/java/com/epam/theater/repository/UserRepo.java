package com.epam.theater.repository;

import com.epam.theater.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByUsernameIgnoreCaseOrMobile(String username, String mobile);
    User findByUsername(String username);
    User findUserByEmail(String email);
}
